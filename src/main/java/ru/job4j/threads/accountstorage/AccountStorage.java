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
        boolean res = false;
        if (account != null) {
            accounts.put(account.id(), account);
            res = true;
        }
        return res;
    }

    public boolean update(Account account) {
        return add(account);
    }

    public synchronized void delete(int id) {
        Optional<Account> acc = getById(id);
        if (acc.isPresent()) {
            accounts.remove(acc.get().id());
        }
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.values().stream()
                .filter(e -> e.id() == id)
                .findFirst();
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account accountSrc = getById(fromId).get();
        Account accountDst = getById(toId).get();
        if (accountSrc != null && accountDst != null && accountSrc.amount() >= amount) {
            update(new Account(accountDst.id(), accountDst.amount() + amount));
            update(new Account(accountSrc.id(), accountSrc.amount() - amount));
            rsl = true;
        }
        return rsl;
    }
}
