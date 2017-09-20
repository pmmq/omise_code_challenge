package com.omise.assignment.donate;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.charity.CharityModel;
import com.omise.assignment.databinding.ActivityDonateBinding;

public class DonateActivity extends BaseActivity {
	
	
	public static final String CHARITY_KEY = "CHARITY_KEY";
	CharityModel mCharityModel;
	
	ActivityDonateBinding mDonateBinding;
	
	@Override
	protected boolean init(final Bundle saveInstanceState) {
		mCharityModel = getIntent().getExtras().getParcelable(CHARITY_KEY);
		return true;
	}
	
	@Override
	protected void injectDependencies() {
		TumBoonApplication.getApplication().getApplictionComponent().inject(this);
	}
	
	@Override
	protected void bindData(final ViewDataBinding mBinding) {
		mDonateBinding = (ActivityDonateBinding) mBinding;
		mDonateBinding.setActivity(this);
	}
	
	@Override
	protected int getResView() {
		return R.layout.activity_donate;
	}
}
