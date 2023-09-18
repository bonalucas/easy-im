package com.easyim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义线程池属性注入类
 *
 * @author 单程车票
 */
@Data
@ConfigurationProperties(prefix = "thread")
public class ThreadPoolConfigProperties {

    /**
     * 核心线程数
     */
    private Integer coreSize;

    /**
     * 最大线程数
     */
    private Integer maxSize;

    /**
     * 空闲存活时间
     */
    private Integer keepAliveTime;

    /**
     * 工作队列长度
     */
    private Integer queueSize;

}
