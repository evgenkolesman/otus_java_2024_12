package ru.otus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtmOpsTest {
    private AtmOpsImpl atmOps;

    @BeforeEach
    void setUp() {
        atmOps = new AtmOpsImpl(100, 50, 20, 10);
        atmOps.deposit(100, 10);
        atmOps.deposit(50, 20);
        atmOps.deposit(20, 100);
        atmOps.deposit(10, 100);
    }

    @Test
    void testInitialBalance() {
        assertEquals(5000, atmOps.getBalance());
    }

    @Test
    void testDeposit() {
        atmOps.deposit(50, 5);
        assertEquals(5250, atmOps.getBalance());
    }

    @Test
    void testCashAcquisitionSuccessful() {
        assertTrue(atmOps.cashAcquisition(380));
        assertEquals(4620, atmOps.getBalance());
    }

    @Test
    void testCashAcquisitionExactDenominations() {
        assertTrue(atmOps.cashAcquisition(200));
        assertEquals(4800, atmOps.getBalance());
    }

    @Test
    void testCashAcquisitionImpossibleAmount() {
        assertFalse(atmOps.cashAcquisition(385));
        assertEquals(5000, atmOps.getBalance());
    }

    @Test
    void testBalanceCheck() {
        assertEquals(5000, atmOps.getBalance());
    }
}
