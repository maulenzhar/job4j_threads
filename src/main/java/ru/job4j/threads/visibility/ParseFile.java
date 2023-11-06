package ru.job4j.threads.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;
    private int data;

    public ParseFile(File file) {
        synchronized (this) {
            this.file = file;
        }
    }

    public synchronized String getContent() {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
        String output = "";
        data = i.read();
        while (content(c -> c > 0) || (content(c -> c < 0x80) && data != -1)) {
            output += (char) data;
            data = i.read();
        }
        return output;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean content(Predicate<Integer> filter) {
        return filter.test(data);
    }
}
