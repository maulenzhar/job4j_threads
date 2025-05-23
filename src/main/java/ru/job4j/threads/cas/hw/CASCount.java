package ru.job4j.threads.cas.hw;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.threads.cas.CASStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {

    }

    public int get() {

    }
}