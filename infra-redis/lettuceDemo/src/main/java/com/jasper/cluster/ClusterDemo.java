package com.jasper.cluster;

import com.jasper.config.RedisClusterManager;

import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

public class ClusterDemo {
    public static void main(String[] args) {
        // 关闭当前的 Redis 连接 相当于多路复用的channel
        try (StatefulRedisClusterConnection<String, String> connection = RedisClusterManager.getConnection();) {
            RedisAdvancedClusterCommands<String, String> commands = connection.sync();
            String key = "k1";
            commands.set(key, "v1");
            String string = commands.get(key);
            System.out.println(string);
        }

    }
}
