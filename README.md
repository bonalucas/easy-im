# Title

EasyIM 服务器

一款基于Netty搭建的即时通讯服务器，内部实现了基于TCP协议的私有协议栈、Protostuff序列化方式、长连接握手认证、心跳检测机制、异常处理机制等功能

EasyIM Android 客户端地址：[https://github.com/bonalucas/easy-im-android](https://github.com/bonalucas/easy-im-android)

## Environment

```
- JDK 8
- netty 4.1.36.Final
- SpringBoot 2.7.4
- protostuff 1.0.10
```

## Usage

### clone

```
git clone https://github.com/bonalucas/easy-im.git
```

### configuration

```yaml
# netty 配置（根据自己需求配置，需要保证客户端可以访问到服务器）
netty:
  ip: 0.0.0.0
  port: 9043
```

### Tree

```
├─logs    // 系统日志
├─src
   ├─main
   │  ├─java
   │  │  └─com
   │  │      └─easyim
   │  │          ├─comm
   │  │          │  ├─message       // 自定义通信消息体
   │  │          │  └─protocol      // 编解码器及序列化器
   │  │          ├─common           // 基础包
   │  │          ├─pojo             // pojo类
   │  │          └─server           // netty 服务器核心包
   │  │              └─handler      // ChannelHandler
   │  │                  └─biz
   │  └─resources       // 资源包
   └─test       // Channel Test
```

## License

Apache-2.0 license
