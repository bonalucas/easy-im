package com.easyim;

import com.easyim.comm.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Easy-IM 服务器启动类
 *
 * @author 单程车票
 */
@Slf4j
@SpringBootApplication
public class EasyIMApplication implements CommandLineRunner {

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
        // 启动 Netty 服务器
        nettyServer.startServer();
    }

}
