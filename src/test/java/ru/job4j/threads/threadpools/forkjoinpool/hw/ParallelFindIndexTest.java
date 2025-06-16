package ru.job4j.threads.threadpools.forkjoinpool.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.job4j.threads.threadpools.forkjoinpool.hw.ParallelFindIndex.run;

class ParallelFindIndexTest {

    @Test
    public void whenStringTypAndMoreThan10() {
        String[] words = {"alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota", "kappa", "kappa1", "kappa23"};
        String target = "kappa23";

        Integer res = run(words, 0, words.length - 1, target);

        Assertions.assertEquals(11, res.intValue());
    }

    @Test
    public void whenIntTypAndLessThan10() {
        Integer[] words = {1, 2, 3, 4, 5, 6, 7};
        Integer target = 7;

        Integer res = run(words, 0, words.length - 1, target);

        Assertions.assertEquals(6, res.intValue());
    }

    @Test
    public void whenIndexNotFound() {
        Integer[] words = {1, 2, 3, 4, 5, 6, 7};
        Integer target = 8;

        Integer res = run(words, 0, words.length - 1, target);

        Assertions.assertEquals(-1, res.intValue());
    }

}