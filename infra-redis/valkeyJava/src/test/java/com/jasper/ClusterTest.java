package com.jasper;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.valkey.ConnectionPoolConfig;
import io.valkey.HostAndPort;
import io.valkey.JedisCluster;

public class ClusterTest {

    private static final int DEFAULT_TIMEOUT = 2000;
    private static final int DEFAULT_REDIRECTIONS = 5;
    private static JedisCluster jc;

    @BeforeEach
    public void init() {

        ConnectionPoolConfig config = new ConnectionPoolConfig();
        config.setMaxTotal(32);
        config.setMaxIdle(32);
        config.setMinIdle(16);
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6380));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6381));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6382));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6383));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6384));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6385));
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6386));
        jc = new JedisCluster(jedisClusterNode, DEFAULT_TIMEOUT,
                DEFAULT_TIMEOUT, DEFAULT_REDIRECTIONS,
                "passwd", null, config);
    }

    @Test
    public void testCluster() {
        String key = "key";
        jc.set(key, "value"); // Note that there is no need to call jc.close
        String value = jc.get(key);
        System.out.println(value);
        jc.close(); // when app exit, close the resource.
    }

}
