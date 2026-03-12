package com.jasper;

import redis.clients.jedis.Jedis;

public class Hello {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("passwd");
        jedis.set("hello", "world");
        jedis.close();
    }
}
