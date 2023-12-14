package com.example.investio;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceholderApi {

    @GET("query?function=TIME_SERIES_DAILY&symbol=RELIANCE.BSE&outputsize=full&apikey=EON3XDHZXVAC03U7")  // Replace "yourApiEndpoint" with the actual API endpoint
    Call<ApiResponse> getApiResponse();
}
