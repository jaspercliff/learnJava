package com.jasper;

import io.valkey.Jedis;
import io.valkey.JedisPool;
import io.valkey.JedisPoolConfig;

public class Pool {
    // valkye-java 基于阻塞式io,一个时间只能使用一个连接，线程不安全，所以需要使用连接池来管理连接资源，
    // 避免频繁创建和销毁连接带来的性能问题。
    private static io.valkey.JedisPool jedisPool;

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        // It is recommended that you set maxTotal = maxIdle = 2*minIdle for best
        // performance
        config.setMaxTotal(32);
        config.setMaxIdle(32);
        config.setMinIdle(16);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 2000, "passwd");
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("key", "value");
            System.out.println(jedis.get("key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        jedisPool.close(); // when app exit, close the resource.
    }
}
