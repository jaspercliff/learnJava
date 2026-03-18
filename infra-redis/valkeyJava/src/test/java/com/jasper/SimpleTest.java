package com.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Stopwatch;

import io.valkey.Jedis;
import io.valkey.Pipeline;

public class SimpleTest {
    private Jedis jedis;

    @BeforeEach
    public void init() {
        jedis = new Jedis("127.0.0.1", 6380);
        jedis.auth("passwd");
    }

    @AfterEach
    public void destory() {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Test
    public void testBasicSetGet() {
        jedis.set("key1", "value1");
        String value = jedis.get("key1");
        System.out.println(value);
        assertEquals("value1", value);
    }

    @Test
    public void flushall() {
        jedis.flushAll();
    }

    @Test
    public void testFor() {
        Stopwatch stopWatch = Stopwatch.createStarted();
        for (int i = 1; i < 100000; i++) {
            jedis.set("test:key" + i, "value" + i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.elapsed(TimeUnit.MILLISECONDS)); // 693ms
    }

    @Test
    public void testMset() {
        String[] arr = new String[2000];
        int j;
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 1; i < 100000; i++) {
            // arr = key value key value key value
            // 相当于 * 2
            j = (i % 1000) << 1;
            arr[j] = "test:key" + i;
            arr[j + 1] = "value" + i;
            if (j == 0) {
                // 当满1000个 mset一次
                jedis.mset(arr);
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS)); // 49ms
    }

    @Test
    void testPipeline() {
        Pipeline pipeline = jedis.pipelined();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("test:key", "value_" + i);
            if (i % 1000 == 0) {
                pipeline.sync();
            }
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));// 74ms
    }
}
