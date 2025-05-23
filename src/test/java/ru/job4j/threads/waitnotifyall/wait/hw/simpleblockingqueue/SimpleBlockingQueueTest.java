package ru.job4j.threads.waitnotifyall.wait.hw.simpleblockingqueue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(e ->
                            {
                                try {
                                    queue.offer(e);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                    Thread.currentThread().interrupt();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}