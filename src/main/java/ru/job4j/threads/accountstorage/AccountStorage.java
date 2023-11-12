package ru.job4j.threads.accountstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(getById(id).get().id());
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> accountSrc = Optional.ofNullable(getById(fromId)).get();
        Optional<Account> accountDst = Optional.ofNullable(getById(toId)).get();
        if (accountSrc.isPresent() && accountDst.isPresent() && accountSrc.get().amount() >= amount) {
            update(new Account(accountDst.get().id(), accountDst.get().amount() + amount));
            update(new Account(accountSrc.get().id(), accountSrc.get().amount() - amount));
            rsl = true;
        }
        return rsl;
    }
}
