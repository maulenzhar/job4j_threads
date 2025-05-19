package ru.job4j.threads.waitnotifyall.wait.hw.barrier;

public class Main {
    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(2);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " count");
                    countBarrier.count();
                    countBarrier.count();
                },
                "count"
        );
        Thread slave = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " await");
                },
                "await"
        );
        master.start();
        slave.start();
    }
}
