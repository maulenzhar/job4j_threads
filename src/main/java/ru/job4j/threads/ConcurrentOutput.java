package ru.job4j.threads;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();

        System.out.println(Thread.currentThread().getName());
    }
}
