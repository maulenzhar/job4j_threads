package ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue;

public class Main {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(2);

        try {
            Thread producer = new Thread(
                    () -> {
                        int value1 = 12;
                        simpleBlockingQueue.offer(value1);
                        System.out.println(Thread.currentThread().getName() + " offered: " + value1);
                    },
                    "producer"
            );
            Thread consumer = new Thread(
                    () -> {
                        try {
                            Integer poll = simpleBlockingQueue.poll();
                            System.out.println(Thread.currentThread().getName() + " poll: " + poll);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    },
                    "consumer"
            );
            producer.start();
            consumer.start();
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
