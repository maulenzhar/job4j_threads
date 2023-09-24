package ru.job4j.threads;

public class ThreadSleep {
    public static void main(String[] args) {
       /* Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(30000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");*/

        Thread sec = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 100; i++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + i  + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        sec.start();
    }
}

