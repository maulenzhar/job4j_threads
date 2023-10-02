package ru.job4j.threads.hw;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = new char[] {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (char proc : process) {
                try {
                    Thread.sleep(500);
                    System.out.print("\r load: " + proc);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }
}
