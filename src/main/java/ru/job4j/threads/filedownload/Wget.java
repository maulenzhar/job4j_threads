package ru.job4j.threads.filedownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileName);
        String url = this.url;
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[speed];
            int bytesRead;
            double countBytesRead = 0;
            var downloadAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                countBytesRead += bytesRead;
                if (countBytesRead > speed) {
                    long ms = System.currentTimeMillis() - downloadAt;
                    var byteInMs = countBytesRead / ms;
                    var res = byteInMs / speed;
                    Thread.sleep((long) res);
                }

                System.out.println("Read " + countBytesRead + " bytes : " + (System.nanoTime() - downloadAt) + " ns");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length <= 1) {
            throw new IllegalStateException("args could not be empty.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
