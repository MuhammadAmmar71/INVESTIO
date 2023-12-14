package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
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


        recyclerView = findViewById(R.id.recyclerView);
        textViewResult = findViewById(R.id.textview);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApiAdapter();
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")  // Replace "yourBaseUrl" with the actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderApi jsonPlaceholderApi = retrofit.create(JSONPlaceholderApi.class);

        Call<ApiResponse> call = jsonPlaceholderApi.getApiResponse();

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
                    // Update RecyclerView data
                    List<TimeSeriesDaily> dataList;
                    dataList = new ArrayList<>(timeSeriesDailyMap.values());
                    adapter.setData(dataList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}