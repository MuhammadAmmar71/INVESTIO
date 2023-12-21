package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HealthUSAStocks extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewResult;
    private ApiAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_usastocks);



        DatabaseClass db = new DatabaseClass(this);





        recyclerView = findViewById(R.id.recyclerView);


//         Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApiAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<CompanyData> companyDataList = new ArrayList<>();


// Assuming you want to start fetching from the 10th row (index 9 as indexing starts from 0)
        int startingIndex = 10;
        // Fetch data from the database
        List<CompanyData> databaseData = db.readingStockslist(startingIndex);
        companyDataList.addAll(databaseData);

        // Add multiple symbols to the list
        List<String> symbols = new ArrayList<>();
//        symbols.add("JNJ");
//        symbols.add("UNH");
//        symbols.add("PFE");
//        symbols.add("MRK");
//        symbols.add("ABT");
//        symbols.add("AMGN");
//        symbols.add("TMO");
//        symbols.add("MDT");
//        symbols.add("GILD");
//        symbols.add("BMY");
        // Fetch data from the API
        for (String symbol : symbols) {
            callingapi(symbol, companyDataList);
        }
    }

    void callingapi(String symbol, List<CompanyData> companyDataList ) {
        //...

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderApi jsonPlaceholderApi = retrofit.create(JSONPlaceholderApi.class);

        Call<ApiResponse> call = jsonPlaceholderApi.getApiResponse(symbol);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                ApiResponse apiResponse = response.body();
                Map<String, TimeSeriesDaily> timeSeriesDailyMap = apiResponse.getTimeSeriesDailyMap();

                if (timeSeriesDailyMap != null) {
                    // Fetch the first entry from the map
                    TimeSeriesDaily firstTimeSeriesDaily = timeSeriesDailyMap.values().iterator().next();



                    // Update data in the CompanyData list based on symbol match
                    for (CompanyData companyData : companyDataList) {
                        if (companyData.getStockSymbol().equals(symbol)) {
                            // Update the CompanyData object with data from the API
                            companyData.setClose(firstTimeSeriesDaily.getClose());
                            break; // Once found and updated, break the loop
                        }
                    }

                    // After fetching data from the API and updating companyDataList, set the updated data in the adapter
                    adapter.setData(companyDataList);
                }
            }



            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}















