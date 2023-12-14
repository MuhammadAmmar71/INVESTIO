package com.example.investio;

import com.google.gson.annotations.SerializedName;

public class TimeSeriesDaily {

    @SerializedName("1. open")
    private String open;

    @SerializedName("2. high")
    private String high;

    @SerializedName("3. low")
    private String low;

    @SerializedName("4. close")
    private String close;

    @SerializedName("5. volume")
    private String volume;

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getVolume() {
        return volume;
    }
}
