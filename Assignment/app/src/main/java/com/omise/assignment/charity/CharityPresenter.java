package com.omise.assignment.charity;

import java.util.List;

import javax.inject.Inject;

import com.omise.assignment.TumBoonService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityPresenter implements CharityContract.Presenter {
	
	@Inject TumBoonService mService;
	
	CharityContract.View mView;
	
	public CharityPresenter(final CharityContract.View view) {
		mView = view;
		mView.getAppComponent().inject(this);
	}
	
	@Override
	public void onDetach() {
		mView = null;

	}
	
	@Override
	public void fetchCharity() {
		try {
			mView.onLoading(true);
			Call<List<CharityModel>> call = mService.getCharities();
			call.enqueue(new Callback<List<CharityModel>>() {
				@Override
				public void onResponse(final Call<List<CharityModel>> call,
						final Response<List<CharityModel>> response) {
					mView.onLoading(false);
					if (response.body() != null) {
						mView.onFetched(response.body());
					} else {
						mView.onFetched(null);
					}
				}
				
				@Override
				public void onFailure(final Call<List<CharityModel>> call, final Throwable t) {
					mView.onLoading(false);
					mView.onFetched(null);
				}
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
