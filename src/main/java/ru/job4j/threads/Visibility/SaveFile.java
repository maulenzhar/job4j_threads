package ru.job4j.threads.Visibility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        synchronized(this) {
            this.file = file;
        }
    }

    public void saveContent(String content) {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
