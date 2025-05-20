package ru.job4j.threads.waitnotifyall.wait.hw.parallelsearch;

import ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }

        ).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.interrupt();
    }
}