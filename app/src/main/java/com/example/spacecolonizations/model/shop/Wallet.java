package com.example.spacecolonizations.model.shop;

public class Wallet {
    private static Wallet instance;
    private int balance;

    public static Wallet getInstance() {
        if (instance == null) {
            instance = new Wallet();
        }
        return instance;
    }

    private Wallet() {
        //starting balance
        this.balance = 100;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * for use in missions and station repair
     * @param amount
     */
    public void addBalance(int amount) {
        balance += amount;
    }


    /**
     * for use in shop
     * @param amount
     */
    public void reduceBalance(int amount) {
        if (balance < amount) {
            return;
        }
        balance -= amount;
    }

    /**
     * only use this when saving from file
     * @param savedBalance
     */
    public void restoreBalance(int savedBalance) {
        balance = savedBalance;
    }
}
