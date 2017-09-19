package com.omise.assignment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface CharityService {
	
	@GET(ServiceEndpoint.CHARITIES_LIST)
	Call<ResponseBody> getCharities();
	
	@POST(ServiceEndpoint.DONATE)
	Call<ResponseBody> donate(@Path("id")int id , @Query("customer_name") String cusName, @Query("card_token") String card_token, @Query("donation_amount") String amount);
	
}
