package ru.job4j.threads;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();

        another.run(); /*метод run не дает указания выполнить свои
         операторы в отдельной нити, как это делаем метод Thread#start.
         Метод run напрямую вызывает операторы в той же нити, в к
         оторой запущен этот метод.*/

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();

        System.out.println(Thread.currentThread().getName());
    }
}
