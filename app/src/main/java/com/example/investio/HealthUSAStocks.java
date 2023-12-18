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

        //...

        DatabaseClass db = new DatabaseClass(this);



        db.addTechUSA("Apple Inc.","APPL");
        db.addTechUSA("Microsoft Corporation","MSFT");
        db.addTechUSA("Amazon.com Inc","AMZN");
//        db.addTechUSA("Alphabet Inc.","GOOGL");
//        db.addTechUSA("MetaPlatforms Inc.","FB");
//        db.addTechUSA("Tesla Inc.","TSLA");
//        db.addTechUSA("Adobe Inc.","ADBE");
//        db.addTechUSA("NVIDIA Corporation","NVDA");
//        db.addTechUSA("Salesforce.com Inc.","CRM");
//        db.addTechUSA("PayPal Holdings Inc.","PYPL");

        recyclerView = findViewById(R.id.recyclerView);
        textViewResult = findViewById(R.id.textview);

//         Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApiAdapter();
        recyclerView.setAdapter(adapter);

        List<CompanyData> companyDataList = new ArrayList<>();



        // Fetch data from the database
        List<CompanyData> databaseData = db.getAllTechUSA();
        companyDataList.addAll(databaseData);

        // Add multiple symbols to the list
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        symbols.add("MSFT");
        symbols.add("AMZN");
        int i=0;
        // Fetch data from the API
        for (String symbol : symbols) {
            i++;
            callingapi(symbol, companyDataList,i);
        }
    }

    void callingapi(String symbol, List<CompanyData> companyDataList ,int i) {
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

                    // Get company name and symbol from the CompanyData object in the list
                    if (!companyDataList.isEmpty()) {
                        CompanyData companyData = companyDataList.get(i); // You might want to adjust this based on your requirements

                        // Create a CompanyData object with data from the database and API
                        CompanyData updatedCompanyData = new CompanyData(
                                companyData.getCompanyName(),
                                companyData.getCompanySymbol(),
                                firstTimeSeriesDaily.getClose()
                        );

                        // Add the data to the existing data in the adapter
                        adapter.addData(Collections.singletonList(updatedCompanyData));
                    }
                }
            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_health_usastocks);
//
//
//        DatabaseClass db = new DatabaseClass(this);
//
//
//        db.addTechUSA("Apple Inc.","APPL");
//        db.addTechUSA("Microsoft Corporation","MSFT");
//        db.addTechUSA("Amazon.com Inc","AMZN");
////        db.addTechUSA("Alphabet Inc.","GOOGL");
////        db.addTechUSA("MetaPlatforms Inc.","FB");
////        db.addTechUSA("Tesla Inc.","TSLA");
////        db.addTechUSA("Adobe Inc.","ADBE");
////        db.addTechUSA("NVIDIA Corporation","NVDA");
////        db.addTechUSA("Salesforce.com Inc.","CRM");
////        db.addTechUSA("PayPal Holdings Inc.","PYPL");
//
//
//
//        recyclerView = findViewById(R.id.recyclerView);
//        textViewResult = findViewById(R.id.textview);
//
//        // Set up RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ApiAdapter();
//        recyclerView.setAdapter(adapter);
//
//        // Add multiple symbols to the list
//        List<String> symbols = new ArrayList<>();
//        symbols.add("AAPL");
//        symbols.add("IBM");
////        symbols.add("FB");
//        symbols.add("AMZN");
//
//        // Call API for each symbol
//        for (String symbol : symbols) {
//            callingapi(symbol);
//        }
//    }
//
//    void callingapi(String symbol) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.alphavantage.co/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JSONPlaceholderApi jsonPlaceholderApi = retrofit.create(JSONPlaceholderApi.class);
//
//        Call<ApiResponse> call = jsonPlaceholderApi.getApiResponse(symbol);
//
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//
//                ApiResponse apiResponse = response.body();
//
//                Map<String, TimeSeriesDaily> timeSeriesDailyMap = apiResponse.getTimeSeriesDailyMap();
//

//
//
//                if (timeSeriesDailyMap != null) {
//                    // Fetch the first entry from the map
//                    TimeSeriesDaily firstTimeSeriesDaily = timeSeriesDailyMap.values().iterator().next();
//
//                    // Now you can use 'firstTimeSeriesDaily' as needed
//                    // For example, you might want to update your RecyclerView data with only the first item
//                    List<TimeSeriesDaily> dataList = Collections.singletonList(firstTimeSeriesDaily);
//                    adapter.addData(dataList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
//    }
//}
