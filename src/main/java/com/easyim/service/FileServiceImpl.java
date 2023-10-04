package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import com.easyim.dal.dataobject.FileDO;
import com.easyim.dal.mapper.FileMapper;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件业务实现类
 *
 * @author 单程车票
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final String BUCKET = "easyim";

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private MinioClient minioClient;

    @Override
    public String uploadFile(String fileName, long fileSize, byte[] fileContent) {
        // 根据后缀名获取类型
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String fileType = getMimeType(extension);
        // 获取文件目录路径名
        String contentPath = getDefaultFolderPath();
        // 获取文件内容的临时文件本地地址
        String localFilePath = getLocalFilePath(fileContent);
        // 获取文件md5名
        String fileMd5 = getFileMd5(new File(localFilePath));
        // 生成objectName
        String objectName = contentPath + fileMd5 + extension;
        // 上传文件至 minio
        boolean res = uploadFileToMinIO(localFilePath, fileType, objectName);
        if (res) {
            // 文件访问地址
            String fileUrl = "/" + BUCKET + "/" + objectName;
            // 文件信息入库
            fileMapper.insert(new FileDO(null, fileName, fileType, objectName, fileUrl, (long) fileContent.length, DateUtil.date()));
            return fileUrl;
        } else {
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 上传文件到 minio
     * @param localFilePath 本地文件地址
     * @param mimeType 文件类型
     * @param objectName 文件名
     * @return 上传结果反馈
     */
    public boolean uploadFileToMinIO(String localFilePath, String mimeType, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(BUCKET)
                    .filename(localFilePath)
                    .object(objectName)
                    .contentType(mimeType)
                    .build();
            // 上传文件
            minioClient.uploadObject(uploadObjectArgs);
            log.info("上传文件到 minio 成功,bucket:{},objectName:{}", BUCKET, objectName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", BUCKET, objectName, e.getMessage());
        }
        return false;
    }

    /**
     * 获取文件默认存储目录路径 年/月/日
     * @return 返回文件目录路径
     */
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/")+"/";
    }

    /**
     * 根据扩展名获取 mimeType
     * @param extension 拓展名
     * @return mimeType
     */
    private String getMimeType(String extension) {
        if(extension == null){
            extension = "";
        }
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        // 默认通用mimeType，字节流
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(extensionMatch != null){
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    /**
     * 根据字节数组创建临时文件
     * @param fileContent 文件内容
     * @return 临时文件本地地址
     */
    private String getLocalFilePath(byte[] fileContent) {
        // 创建临时文件
        File temp = null;
        try {
            temp = File.createTempFile("minio", ".temp");
        } catch (IOException e) {
            log.error("临时文件创建出错");
        }
        // 写入文件内容
        try (FileOutputStream fileOutputStream = new FileOutputStream(temp)) {
            fileOutputStream.write(fileContent);
        } catch (IOException e) {
            log.error("文件内容写入临时文件出错");
        }
        return temp.getAbsolutePath();
    }

    /**
     * 获取文件的md5
     * @param file 指定文件路径
     * @return 文件的MD5
     */
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
