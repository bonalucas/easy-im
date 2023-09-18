package com.easyim;

import com.easyim.server.NettyServer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Easy-IM 服务器启动类
 *
 * @author 单程车票
 */
@Slf4j
@SpringBootApplication
public class EasyIMApplication implements CommandLineRunner {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(EasyIMApplication.class, args);
    }

    /**
     * 实现 CommandLineRunner 接口的 run 方法在服务启动时执行方法内的内容
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("NettyServer 启动中...");
        Future<Channel> future = executor.submit(nettyServer);
        Channel channel = future.get();
        if (null == channel) {
            throw new RuntimeException("NettyServer 启动失败 => Channel is null");
        }
        while (!channel.isActive()) {
            log.info("NettyServer 启动中...");
            Thread.sleep(500);
        }
        log.info("NettyServer 启动成功 => Channel：{}", channel.localAddress());
    }
}
