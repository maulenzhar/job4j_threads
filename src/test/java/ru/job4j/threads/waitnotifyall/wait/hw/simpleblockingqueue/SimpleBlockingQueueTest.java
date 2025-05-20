package ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class SimpleBlockingQueueTest {
    @Test
    public void testProducerConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);

        final int[] result = new int[1];

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer started");
                queue.offer(42);
                System.out.println("Producer finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            System.out.println("Consumer started");
            try {
                result[0] = queue.poll();
                System.out.println("Consumer received value: " + result[0]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Consumer finished");
        });

        producer.start();
        producer.join();

        consumer.start();
        consumer.join();

        assertEquals(42, result[0]);
    }
}