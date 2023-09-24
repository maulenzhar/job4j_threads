package ru.job4j.threads.hw;

public class ConsoleProgress implements Runnable {
    char[] process = new char[] {'-', '\\', '|', '/'};

    @Override
    public void run() {
        for (int i = 0; i < process.length; i++) {
            try {
                Thread.sleep(500);
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\r load: " + process[i]);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
