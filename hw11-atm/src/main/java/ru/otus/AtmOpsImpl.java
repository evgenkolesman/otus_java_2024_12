package ru.otus;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtmOpsImpl implements AtmOps {

    private final CashStorage cashStorage;
    private final CashAcquisitionStrategy cashAcquisitionStrategy;

    public AtmOpsImpl(int... denominations) {
        this.cashStorage = new CashStorage(denominations);
        this.cashAcquisitionStrategy = new CashAcquisitionStrategy();
    }

    @Override
    public void deposit(int denomination, int count) {
        cashStorage.addCash(denomination, count);
    }

    @Override
    public boolean cashAcquisition(int amount) {
        Map<Integer, Integer> accureCashPlan = cashAcquisitionStrategy.calculateWithdrawal(cashStorage, amount);
        if (accureCashPlan.isEmpty()) {
            log.info("Cannot dispense requested amount");
            return false;
        }
        cashStorage.accuseCash(accureCashPlan);
        log.info("Dispensed: {}", accureCashPlan);
        return true;
    }

    @Override
    public int getBalance() {
        return cashStorage.getTotalBalance();
    }
}
