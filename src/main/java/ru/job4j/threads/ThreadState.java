package ru.job4j.threads;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        System.out.println(second.getState());

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("работа завершена ...");
            System.out.println(first.getState());
            System.out.println(second.getState());
        }

        System.out.println("работа завершена");
    }
}
