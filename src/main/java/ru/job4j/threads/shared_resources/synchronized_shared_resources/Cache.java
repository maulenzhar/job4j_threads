package ru.job4j.threads.shared_resources.synchronized_shared_resources;

public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
