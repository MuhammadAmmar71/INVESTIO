package com.example.investio;

public class UserPortfolioModelClass {

    String walletID;
    int portfolioID;
    String percentInStock;

    public UserPortfolioModelClass(String walletID, int portfolioID, String percentInStock) {
        this.walletID = walletID;
        this.portfolioID = portfolioID;
        this.percentInStock = percentInStock;
    }

    public String getWalletID() {
        return walletID;
    }

    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    public int getPortfolioID() {
        return portfolioID;
    }

    public void setPortfolioID(int portfolioID) {
        this.portfolioID = portfolioID;
    }

    public String getPercentInStock() {
        return percentInStock;
    }

    public void setPercentInStock(String percentInStock) {
        this.percentInStock = percentInStock;
    }
}
