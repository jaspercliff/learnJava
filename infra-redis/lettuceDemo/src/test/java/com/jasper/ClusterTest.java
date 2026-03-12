package com.jasper;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

public class ClusterTest {

    private RedisClusterClient client;

    @BeforeEach
    public void before() {
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
    }

    @AfterEach
    void after() {
        if (client != null) {
            client.shutdown();
        }
    }

    @Test
    public void test() {
        try (StatefulRedisClusterConnection<String, String> connection = client.connect()) {
            // 5. 获取同步命令对象 默认异步的
            RedisAdvancedClusterCommands<String, String> syncCommands = connection.sync();
            // 执行操作
            syncCommands.set("hello", "valkey-cluster");
            String value = syncCommands.get("hello");

            System.out.println("Result: " + value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
