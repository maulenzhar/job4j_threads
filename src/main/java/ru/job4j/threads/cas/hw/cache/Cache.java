package ru.job4j.threads.cas.hw.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {

        Base base = memory.computeIfPresent(model.id(), (a, b) -> {

            if (!Objects.equals(model.version(), b.version())) {
                throw new OptimisticException("Version are not equal");
            }

            return new Base(model.id(), model.name(), b.version() + 1);
        });

        return base != null;
    }

    public void delete(int id) {
        findById(id).ifPresent(base -> memory.remove(base.id()));
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}