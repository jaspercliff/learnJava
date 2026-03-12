package com.jasper;

import io.valkey.Jedis;

/**
 * jedis 的平替 fork jedis
 */
public class Hello {
    public static void main(String[] args) {
        // 只改了包名，还是Jedis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("passwd");
        jedis.set("key", "value");
        // 旧连接没释放，操作系统的文件句柄（File Descriptors）会被耗尽
        jedis.close();
    }
}
