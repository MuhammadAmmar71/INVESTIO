package com.example.investio;

import java.nio.DoubleBuffer;

public class TransactionsModelClass {

    String walletid;
    Double amount;

    int transactionid;

    String transactiontime;



    public TransactionsModelClass( int transactionid,String walletid, Double amount, String transactiontime) {
        this.walletid = walletid;
        this.amount = amount;
        this.transactionid = transactionid;
        this.transactiontime = transactiontime;
    }


    public int getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(int transactionid) {
        this.transactionid = transactionid;
    }


    public TransactionsModelClass() {
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(String transactiontime) {
        this.transactiontime = transactiontime;
    }
}
