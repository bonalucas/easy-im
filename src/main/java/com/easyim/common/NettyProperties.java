package com.easyim.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义 netty 属性注入类
 *
 * @author 单程车票
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    /**
     * netty 服务器IP
     */
    private String ip;

    /**
     * netty 服务器端口
     */
    private int port;

}
