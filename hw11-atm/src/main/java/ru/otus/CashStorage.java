package ru.otus;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CashStorage {
    private final Map<Integer, Integer> cashStorage = new TreeMap<>(Collections.reverseOrder());

    public CashStorage(int... denominations) {
        for (int denom : denominations) {
            cashStorage.put(denom, 0);
        }
    }

    public void addCash(int denomination, int count) {
        cashStorage.put(denomination, cashStorage.getOrDefault(denomination, 0) + count);
    }

    public Map<Integer, Integer> getCashStorage() {
        return cashStorage;
    }

    public int getTotalBalance() {
        return cashStorage.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    public void accuseCash(Map<Integer, Integer> accuseCashPlan) {
        for (var entry : accuseCashPlan.entrySet()) {
            int denom = entry.getKey();
            int count = entry.getValue();
            cashStorage.put(denom, cashStorage.get(denom) - count);
        }
    }
}
