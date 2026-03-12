package com.jasper.config;

import java.time.Duration;
import java.util.Arrays;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

public class RedisClusterManager {
    // 相当于netty中的eventloopgroup
    public static final RedisClusterClient client;
    // lettuce的连接是多路复用的，只需要建立一个就行
    public static final StatefulRedisClusterConnection<String, String> connect;

    static {
        RedisURI node1 = RedisURI.builder().withHost("localhost").withPort(6380).withPassword("passwd").build();
        RedisURI node2 = RedisURI.builder().withHost("localhost").withPort(6381).withPassword("passwd").build();
        RedisURI node3 = RedisURI.builder().withHost("localhost").withPort(6382).withPassword("passwd").build();
        RedisURI node4 = RedisURI.builder().withHost("localhost").withPort(6383).withPassword("passwd").build();
        RedisURI node5 = RedisURI.builder().withHost("localhost").withPort(6384).withPassword("passwd").build();
        RedisURI node6 = RedisURI.builder().withHost("localhost").withPort(6385).withPassword("passwd").build();
        client = RedisClusterClient
                .create(Arrays.asList(node1, node2, node3, node4, node5, node6));
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                // 当 Valkey/Redis 集群发生主从切换（Failover）时，客户端会因为持有旧的路由表而持续报错
                // .enableAdaptiveRefreshTrigger(RefreshTrigger.MOVED_REDIRECT,
                // RefreshTrigger.PERSISTENT_RECONNECTS)
                // // 限制自适应刷新的频率，防止频繁请求 CLUSTER SLOTS 压垮节点
                .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(5))
                .build();

        client.setOptions(ClusterClientOptions.builder()
                .topologyRefreshOptions(topologyRefreshOptions)
                .build());

        connect = client.connect();
        // 当前 JVM 运行时对象 当 JVM 退出时执行这个线程
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (client != null) {
                client.shutdown();
            }
        }));
    }

    public static StatefulRedisClusterConnection<String, String> getConnection() {
        return connect;
    }
}
