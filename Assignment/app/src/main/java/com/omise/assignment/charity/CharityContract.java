package com.omise.assignment.charity;

import java.util.List;

import com.omise.assignment.applications.bases.IBasePresenter;
import com.omise.assignment.applications.bases.IBaseView;

public class CharityContract  {
	
	public interface View extends IBaseView {
		void onFetched(List<CharityModel> charities);
		void onLoading(boolean isLoading);
	}
	
	public interface Presenter extends IBasePresenter{
		void fetchCharity();
	}
	
}
