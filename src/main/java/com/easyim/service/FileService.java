package com.easyim.service;

/**
 * 文件业务接口
 *
 * @author 单程车票
 */
public interface FileService {

    /**
     * 上传文件
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param fileContent 文件内容
     * @return 文件访问地址
     */
    String uploadFile(String fileName, long fileSize, byte[] fileContent);

}
