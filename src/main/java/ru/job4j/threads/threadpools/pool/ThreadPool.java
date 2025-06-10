package ru.job4j.threads.threadpools.pool;

import ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int size) {
        threads = new LinkedList<>();
        tasks = new SimpleBlockingQueue<>(size);
        init(size);
    }

    private void init(int size) {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                        try {
                            Runnable poll = tasks.poll();
                            poll.run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    )
            );
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutDown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) {
        int size = Runtime.getRuntime().availableProcessors();
        ThreadPool threadPool = new ThreadPool(size);

        for (int i = 0; i < 10; i++) {
            final int jobId = i;
            threadPool.work(() -> {
                System.out.println("Job " + jobId + " executed by " + Thread.currentThread().getName());
            });
        }

        threadPool.shutDown();
    }
}
