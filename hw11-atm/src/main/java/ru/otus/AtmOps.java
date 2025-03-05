package ru.otus;

public interface AtmOps {
    void deposit(int denomination, int count);

    boolean cashAcquisition(int amount);

    int getBalance();
}
