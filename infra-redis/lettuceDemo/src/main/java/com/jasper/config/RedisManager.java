package com.jasper.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisManager {
    // 相当于netty中的eventloopgroup
    public static final RedisClient client;
    // lettuce的连接是多路复用的，只需要建立一个就行
    public static final StatefulRedisConnection<String, String> connect;

    static {
        client = RedisClient.create("redis://passwd@localhost:6379/0");
        connect = client.connect();
        // 当前 JVM 运行时对象 当 JVM 退出时执行这个线程
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (client != null) {
                client.shutdown();
            }
        }));
    }

    public static StatefulRedisConnection<String, String> getConnection() {
        return connect;
    }

}
