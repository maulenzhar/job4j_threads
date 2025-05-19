package ru.job4j.threads;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();

        another.run();
         /*метод run не дает указания выполнить свои
         операторы в отдельной нити, как это делаем метод Thread#start.
         Метод run напрямую вызывает операторы в той же нити, в которой запущен этот метод.

            another.start();
            Запускает новый поток, в котором выполняется Runnable.
            → В консоли будет имя вроде Thread-0, Thread-1, и т.п.

            another.run();
            Просто вызывает метод run() в текущем потоке (например, main).
            Это не запускает новый поток.
            → В консоли будет main.
         */

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();

        System.out.println(Thread.currentThread().getName());
    }
}
