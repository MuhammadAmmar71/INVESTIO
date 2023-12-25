package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

   Toolbar toolbar;
   AppCompatButton btninvesting;

    int startingIndex = 10;

    int portfolioid=((startingIndex/10)+1);

    DatabaseClass db = new DatabaseClass(HealthUSAStocks.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_usastocks);

        toolbar=findViewById(R.id.toolbar);



          btninvesting=findViewById(R.id.btninvesting);


        btninvesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog =new Dialog(HealthUSAStocks.this);
                dialog.setContentView(R.layout.investdialogbox);

                EditText edtamount=dialog.findViewById(R.id.edtamount);

                Button btnInvest=dialog.findViewById(R.id.btninvest);

                btnInvest.setOnClickListener(new View.OnClickListener() {

                    String getamount;
                    Double amountinvest;
                    @Override
                    public void onClick(View view) {
                        getamount=edtamount.getText().toString();
                        amountinvest=Double.parseDouble(getamount);

//                        String timestamp=db.getCurrentTimestamp();
                        String walletid=db.fetchwalletid();
//                        db.storetransactions(amountinvest,timestamp,walletid);

                        int portfolioid=((startingIndex/10)+1);
                        Double percentinstockportfolio;
                        Double amountinwallet=db.readwalletamount();
                        percentinstockportfolio=((amountinvest/amountinwallet)*100);
                        db.adduserportfolio(portfolioid,walletid,percentinstockportfolio);



                        dialog.dismiss();


                    }
                });

                dialog.show();

            }
        });


        recyclerView = findViewById(R.id.recyclerView);




//         Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApiAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<CompanyData> companyDataList = new ArrayList<>();




// Assuming you want to start fetching from the 10th row (index 9 as indexing starts from 0)
        // int startingIndex = 10;
        // Fetch data from the database
        List<CompanyData> databaseData = db.readingStockslist(startingIndex);
        companyDataList.addAll(databaseData);

        // Add multiple symbols to the list
        List<String> symbols = new ArrayList<>();
        symbols.add("JNJ");
        symbols.add("UNH");
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


//                                 FUNCTIONS






    void callingapi(String symbol, List<CompanyData> companyDataList ) {

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

                            //preparing data to store into portfolio table
                            String getStockValue = firstTimeSeriesDaily.getClose();
                            Double stockValue = Double.parseDouble(getStockValue);
                            String timestamp = db.getCurrentTimestamp();


                            if (db.isnullstocksvalue()) {

                                db.storestocksvalue(stockValue, timestamp,portfolioid);

                            }


                            else {

                                int count = db.readportfoliovaluerows(portfolioid);

                               if(count>=0 || count<2){  // set it to  10 later

                                   db.updateStocksValue(portfolioid,stockValue,timestamp);

                               }


                            else {
                                   String currentime= db.getCurrentTimestamp();
                                   String firsttime=db.readFirstStockTime(portfolioid);
                                   int datediff;
                                   datediff=db.difftimestamp(firsttime,currentime);
//next    if next is not equal to the already present next
                                   Double newaverage=db.stocksaverage(portfolioid);
                                   if(!db.findPortfolioid(portfolioid)){
                                       db.insertnewaverage(newaverage,portfolioid);
                                   }
                                   else{
                                       db.updatenewaverage(portfolioid,newaverage);
                                   }

                           if(datediff>=1440){



                                       Double prevaverage;
                                       prevaverage=db.stocksaverage(portfolioid);

                                       db.updatepreviousaverage(portfolioid,newaverage);

                                       db.deleteStocksValueData(portfolioid);





                                       db.updateStocksValue(portfolioid,stockValue,timestamp);
//                                       Double newstocksaverage;
//                                       newstocksaverage=db.stocksaverage(portfolioid);
//
//                                       Double profit=newstocksaverage-previousstocksaverage;
//                                       Double amountinvest;


                                   }
                               }




                            }




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














