package com.omise.assignment.donate;

import co.omise.android.TokenRequest;
import co.omise.android.models.Token;
import com.omise.assignment.applications.bases.IBasePresenter;
import com.omise.assignment.applications.bases.IBaseView;

public interface DonateContract  {
	
	public interface View extends IBaseView{
		void onReceiveToken(Token token);
		void onSuccessDonate(DonateModel donateModel);
	}
	
	public interface Presenter extends IBasePresenter{
		void requestToken(TokenRequest request);
		void makeDonate(int charityId,Token token,String name,String amount);
	}
}
