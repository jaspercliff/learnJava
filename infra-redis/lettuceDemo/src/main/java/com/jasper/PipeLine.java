package com.jasper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.jasper.config.RedisManager;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

public class PipeLine {
    public static void main(String[] args) {
        int totalCount = 100000;
        int batchSize = 1000; // 每 1000 条一段
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (StatefulRedisConnection<String, String> connection = RedisManager.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            // 不能在多线程情况下使用 异步情况下使用 同步模 拟阻塞调用
            connection.setAutoFlushCommands(false);
            ArrayList<RedisFuture<?>> batchFutures = new ArrayList<>(batchSize * 2);

            for (int i = 0; i < totalCount; i++) {
                batchFutures.add(commands.set("key-" + i, "value-" + i));
                batchFutures.add(commands.expire("key-" + i, 3600));

                // 重点：达到分段大小时，发送并等待
                if (i % batchSize == 0 || i == totalCount - 1) {
                    connection.flushCommands(); // 发送这一段

                    // 等待这一段完成
                    LettuceFutures.awaitAll(5, TimeUnit.SECONDS,
                            batchFutures.toArray(new RedisFuture[0]));

                    batchFutures.clear(); // 清空这一段的引用，让 GC 回收内存
                }
            }

            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");// 763

            connection.setAutoFlushCommands(true);
            RedisCommands<String, String> sync = connection.sync();
            stopwatch.reset().start();
            for (int i = 0; i < 100000; i++) {
                sync.set("key" + i, "val" + i);
                sync.expire("key-" + i, 3600);
            }
            stopwatch.stop();
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");// 2176
        }
    }
}
