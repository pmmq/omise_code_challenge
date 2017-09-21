package com.omise.assignment;

import java.util.List;

import com.omise.assignment.charity.CharityModel;
import com.omise.assignment.donate.DonateModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface TumBoonService {
	
	@GET(ServiceEndpoint.CHARITIES_LIST)
	Call<List<CharityModel>> getCharities();
	
	@POST(ServiceEndpoint.DONATE)
	Call<DonateModel> donate(@Path("id") int id, @Query("customer_name") String cusName,
			@Query("card_token") String card_token, @Query("donation_amount") String amount);
	
}
