package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopTechWorldwideStocks extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textViewResult;
    private ApiAdapter adapter;

    Toolbar toolbar;
    AppCompatButton btninvesting;

    int startingIndex = 90;

    int portfolioid=((startingIndex/10)+1);

    DatabaseClass db = new DatabaseClass(TopTechWorldwideStocks.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_usastocks);

        toolbar=findViewById(R.id.toolbar);



        btninvesting=findViewById(R.id.btninvesting);


        btninvesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog =new Dialog(TopTechWorldwideStocks.this);
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


                        if(amountinvest<=db.readwalletamount()){

                            String walletid=db.fetchwalletid();



                            //populating user portfolio
                            int portfolioid=((startingIndex/10)+1);
                            Double percentinstockportfolio;
                            Double amountinwallet=db.readwalletamount();
                            percentinstockportfolio=((amountinvest/amountinwallet)*100);
                            db.adduserportfolio(portfolioid,walletid,percentinstockportfolio);

                            // populating  transactions history table
                            String transactime= db.getCurrentTimestamp();

                            db.populatetransactionshistory(transactime,walletid,amountinvest,portfolioid);

                            Double AmountAfterInvest=amountinwallet-amountinvest;

                            db.updatewalletamount(AmountAfterInvest);



                            // here we will be updating our ui in the wallet
                            // updateWalletAmountUI(AmountAfterInvest);



                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Sorry,You don't have sufficient amount in your wallet",Toast.LENGTH_SHORT).show();
                        }



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







        List<CompanyData> databaseData = db.readingStockslist(startingIndex);
        companyDataList.addAll(databaseData);

        List<String> symbols = new ArrayList<>();
        symbols.add("4739.T");
        symbols.add("2706.T");
        symbols.add("GPLDF");
        symbols.add("ALNAQ");
        symbols.add("GNCA");
        symbols.add("SBNY");
        symbols.add("ZVOI");
        symbols.add("BBIG");
        symbols.add("ALPSQ");
        symbols.add("MNK");



        for (String symbol : symbols) {
           //  callingapi(symbol, companyDataList);
        }





    }


//                                 FUNCTIONS






    void callingapi(String symbol, List<CompanyData> companyDataList ) {

        String APIkey=generateAPIKey();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JSONPlaceholderApi jsonPlaceholderApi = retrofit.create(JSONPlaceholderApi.class);

        Call<ApiResponse> call = jsonPlaceholderApi.getApiResponse(symbol,APIkey);

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
                    TimeSeriesDaily firstTimeSeriesDaily = timeSeriesDailyMap.values().iterator().next();



                    for (CompanyData companyData : companyDataList) {
                        if (companyData.getStockSymbol().equals(symbol)) {
                            companyData.setClose(firstTimeSeriesDaily.getClose());

                            String getStockValue = firstTimeSeriesDaily.getClose();
                            Double stockValue = Double.parseDouble(getStockValue);
                            String timestamp = db.getCurrentTimestamp();


                            if (db.isNullStocksValue()) {
                                System.out.println(db.isNullStocksValue());

                                db.storestocksvalue(portfolioid,stockValue, timestamp);

                            }


                            else
                            {

                                int count = db.readPortfolioValueRows(portfolioid);

                                System.out.println(count);

                                if(count>=1 && count<10){

                                    db.storestocksvalue(portfolioid,stockValue, timestamp);

                                }


                                else
                                {
                                    String currentime= db.getCurrentTimestamp();
                                    String firsttime=db.readFirstStockTime(portfolioid);
                                    int datediff;
                                    datediff=db.difftimestamp(firsttime,currentime);


                                    //   if next is not equal to the already present next
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





                                        db.storestocksvalue(portfolioid,stockValue, timestamp);

                                        Double newstocksaverage;
                                        newstocksaverage=db.stocksaverage(portfolioid);


                                        Double amountinvest=db.readAmountTransaction(portfolioid);
                                        Double profit=newstocksaverage-prevaverage;
                                        Double formulaROI=((amountinvest/prevaverage)*(profit));

                                        // reflecting that value to wallet
                                        Double amountInWallet =  db.readwalletamount();
                                        Double updatedAmount = amountInWallet + formulaROI;
                                        db.updatewalletamount(updatedAmount);
                                        //    reflecting amount in wallet
                                        updateWalletAmountUI(updatedAmount);




                                    }
                                }




                            }



                            break;
                        }
                    }

                    adapter.setData(companyDataList);
                }
            }



            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });



    }


    public void updateWalletAmountUI(Double updatedAmount) {
        EditText edtNewAmount = findViewById(R.id.edtamountwallet);
        edtNewAmount.setText(String.valueOf(updatedAmount));
    }


    public static String generateAPIKey(){

        String [] APIkeys={"Y3FY53X742HFLF11","A6FOXHUUEGIUIR7I","JWIQELY84ASSFMPI","K6T7TZ4N2PSI03BZ","32PZJ27O8UTN9C8A","RRN07H4U1EXZCVGZ","EON3XDHZXVAC03U7","MTICII9DWOLBHI1I","L5SHNN1GY5H22JLC","JR5MCJOWJ27DPBK2","KSO2DKVADL7FK8DD","DXJ48PCZQX12PMQO","23NC6GVQT7FB2EEV","X37R5WFJOCFE9T35","2HHQJB5BEAPMUTFS"};

        //String [] APIkeys={"KSO2DKVADL7FK8DD","DXJ48PCZQX12PMQO","23NC6GVQT7FB2EEV","X37R5WFJOCFE9T35"};

        // String [] APIkeys={"2HHQJB5BEAPMUTFS"};

        Random random=new Random();

        return  APIkeys[random.nextInt(APIkeys.length)];
    }


}














