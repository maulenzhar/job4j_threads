package ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int total;

    public SimpleBlockingQueue(final int total) {
        this.total = total;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == total) {
            this.wait();
        }
        queue.offer(value);
        this.notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T val = queue.poll();
        this.notify();
        return val;
    }
}
