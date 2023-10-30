package ru.job4j.threads.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;
    private Predicate<Integer> pred = c -> c > 0;
    private int data;
    public ParseFile(File file) {
        synchronized (this) {
            this.file = file;
        }
    }

    public synchronized String getContent() throws IOException {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            String output = "";
            data = i.read();
            while (content(pred)) {
                output += (char) data;
                data = i.read();
            }
            return output;
        }
    }

    public synchronized String getContentWithoutUnicode() {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
        String output = "";
        data = i.read();
        while (content(pred)) {
            if (content(c -> c < 0x80)) {
                output += (char) data;
                data = i.read();
            }
        }
        return output;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean content(Predicate<Integer> filter) {
        return filter.test(data);
    }

    public static void main(String[] args) throws IOException {
        ParseFile parseFile = new ParseFile(new File("/Users/maulen/Documents/1.Projects/projects/job4j_design2/log.txt"));
        System.out.println(parseFile.getContent());
        System.out.println(parseFile.getContentWithoutUnicode());
    }
}
