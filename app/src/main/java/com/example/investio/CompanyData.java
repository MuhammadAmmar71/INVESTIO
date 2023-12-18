package com.example.investio;

public class CompanyData {
    private String companyName;
    private String companySymbol;
    private String close;


 //Constructors, getters, and setters

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getClose() {
        return close;
    }

    public CompanyData() {
    }

    // Example constructor:
    public CompanyData(String companyName, String companySymbol, String close) {
        this.companyName = companyName;
        this.companySymbol = companySymbol;
        this.close = close;
    }
}

