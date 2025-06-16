package ru.job4j.threads.threadpools.forkjoinpool.hw;

import ru.job4j.threads.threadpools.forkjoinpool.ParallelMergeSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;

    public ParallelFindIndex(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    @Override
    protected Integer compute() {

        if (array.length <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(obj)) {
                    System.out.printf("Found '%s' at index %d in thread %s%n", obj, i, Thread.currentThread().getName());
                    return i;
                }
            }
        }

        if (to - from == 1) {
            if (array[from + 1].equals(obj)) {
                return from + 1;
            } else {
                return -1;
            }
        }

        int mid = (from + to) / 2;
        ParallelFindIndex<T> leftTask = new ParallelFindIndex<>(array, from, mid, obj);
        ParallelFindIndex<T> rightTask = new ParallelFindIndex<>(array, mid, to, obj);

        leftTask.fork();
        rightTask.fork();

        return rightTask.compute();

    }

    public static <T> Integer run(T[] array, int from, int to, T obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<T> task = new ParallelFindIndex<T>(array, from, to, obj);
        return forkJoinPool.invoke(task);
    }
}
