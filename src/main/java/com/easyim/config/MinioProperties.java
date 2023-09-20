package com.easyim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio 配置属性注入类
 *
 * @author 单程车票
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * 节点地址
     */
    private String endpoint;

    /**
     * 账户
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

}
