package ru.job4j.threads.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        synchronized(this) {
            this.file = file;
        }
    }

    public String getContent() throws IOException {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            String output = "";
            int data;
            while (content(c -> c > 0, data = i.read())) {
                output += (char) data;
            }
            return output;
        }
    }

    public String getContentWithoutUnicode() {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
        String output = "";
        int data;
        while (content(c -> c > 0, data = i.read())) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean content(Predicate<Integer> filter, int c) {
        return filter.test(c);
    }
}
