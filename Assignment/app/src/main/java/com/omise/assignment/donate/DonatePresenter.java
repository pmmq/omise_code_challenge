package com.omise.assignment.donate;

import android.widget.Toast;

import java.security.GeneralSecurityException;

import javax.inject.Inject;

import co.omise.android.Client;
import co.omise.android.TokenRequest;
import co.omise.android.TokenRequestListener;
import co.omise.android.models.Token;
import com.omise.assignment.BuildConfig;
import com.omise.assignment.TumBoonService;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonatePresenter implements DonateContract.Presenter {
	
	DonateContract.View mView;
	
	@Inject TumBoonService mService;
	
	public DonatePresenter(final DonateContract.View view) {
		mView = view;
		mView.getAppComponent().inject(this);
	}
	
	@Override
	public void onDetach() {
		mView = null;
	}
	
	@Override
	public void requestToken(final TokenRequest request) {
		try {
			mView.onLoading(true);
			Client client = new Client(BuildConfig.OMISE_PUBLIC_KEY);
			client.send(request, new TokenRequestListener() {
				@Override
				public void onTokenRequestSucceed(final TokenRequest tokenRequest, final Token token) {
					mView.onLoading(false);
					if (mView != null) {
						mView.onReceiveToken(token);
					}
				}
				
				@Override
				public void onTokenRequestFailed(final TokenRequest tokenRequest, final Throwable throwable) {
					mView.onLoading(false);
					if (mView != null) {
						mView.onReceiveToken(null);
					}
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void makeDonate(int charityId, final Token token, final String name, final String amount) {
		Call<DonateModel> call = mService.donate(charityId, name, token.id, amount);
		mView.onLoading(true);
		call.enqueue(new Callback<DonateModel>() {
			@Override
			public void onResponse(final Call<DonateModel> call, final Response<DonateModel> response) {
				if (mView != null) {
					mView.onLoading(false);
					if (response.body() != null) {
						mView.onSuccessDonate(response.body());
					} else {
						DonateModel error = new DonateModel();
						try {
							JSONObject jObjError = new JSONObject(response.errorBody().string());
							error.setError(jObjError.getString("error"));
							error.setCode(jObjError.getString("code"));
						} catch (Exception e) {
							e.printStackTrace();
						}
						mView.onSuccessDonate(error);
					}
				}
			}
			
			@Override
			public void onFailure(final Call<DonateModel> call, final Throwable t) {
				if (mView != null) {
					mView.onLoading(false);
					mView.onSuccessDonate(null);
				}
			}
		});
	}
}
