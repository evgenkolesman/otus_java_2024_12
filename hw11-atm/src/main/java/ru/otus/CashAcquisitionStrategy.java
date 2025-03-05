package ru.otus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CashAcquisitionStrategy {
    public Map<Integer, Integer> calculateWithdrawal(CashStorage storage, int amount) {
        Map<Integer, Integer> tempStorage = new HashMap<>();
        int remaining = amount;

        for (var entry : storage.getCashStorage().entrySet()) {
            int denom = entry.getKey();
            int available = entry.getValue();
            int needed = Math.min(remaining / denom, available);
            if (needed > 0) {
                tempStorage.put(denom, needed);
                remaining -= needed * denom;
            }
        }

        return remaining == 0 ? tempStorage : Collections.emptyMap();
    }
}
