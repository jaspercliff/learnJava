package com.jasper.cluster;

import com.jasper.config.RedisManager;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class SimpleDemo {
    public static void main(String[] args) {

        try (StatefulRedisConnection<String, String> connect = RedisManager.getConnection()) {
            RedisCommands<String, String> commands = connect.sync();
            String key = "name";
            commands.set(key, "jasper");
            String name = commands.get(key);
            System.out.println(name);
        }
    }
}
