package com.example.investio;

public class CompanyData {

    private String StockName, StockSymbol;
    private int Stockid;

    private String close;

    public String getStockName() {
        return StockName;
    }

    public String getStockSymbol() {
        return StockSymbol;
    }

    public int getStockid() {
        return Stockid;
    }

    public String getClose() {
        return close;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public void setStockSymbol(String stockSymbol) {
        StockSymbol = stockSymbol;
    }

    public void setStockid(int stockid) {
        Stockid = stockid;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public CompanyData(String stockName, String stockSymbol, int stockid, String close) {
        StockName = stockName;
        StockSymbol = stockSymbol;
        Stockid = stockid;
        this.close = close;
    }

    public CompanyData() {

    }
}
