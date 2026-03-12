package com.jasper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.valkey.JedisPool;
import io.valkey.JedisPoolConfig;

public class PoolTest {

    private static JedisPool jedisPool;

    @BeforeEach
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        // It is recommended that you set maxTotal = maxIdle = 2*minIdle for best
        // performance
        config.setMaxTotal(32);
        config.setMaxIdle(32);
        config.setMinIdle(16);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 2000, "passwd");
    }

    @AfterEach
    public void destory() {
        if (jedisPool != null) {
            jedisPool.destroy();
        }
    }
}
