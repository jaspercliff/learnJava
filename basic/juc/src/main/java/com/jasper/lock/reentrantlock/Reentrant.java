package com.jasper.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class Reentrant {
    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Reentrant service = new Reentrant();
        Runnable task =
                service::a;
        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
    }

    public void a() {
        lock.lock();
        try {
            System.out.println("outter");
            b();
        } finally {
            lock.unlock();
        }
    }

    public void b() {
        lock.lock(); // 重入 不会死锁
        try {
            System.out.println("inner");
        } finally {
            lock.unlock();
        }
    }
}
