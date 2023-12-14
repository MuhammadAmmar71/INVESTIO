package com.example.investio;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiResponse {
    @SerializedName("Time Series (Daily)")
    private Map<String, TimeSeriesDaily> timeSeriesDailyMap;



    public Map<String, TimeSeriesDaily> getTimeSeriesDailyMap() {
        return timeSeriesDailyMap;
    }
}
