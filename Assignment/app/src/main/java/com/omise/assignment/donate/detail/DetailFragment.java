package com.omise.assignment.donate.detail;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;

import com.omise.assignment.R;
import com.omise.assignment.applications.bases.BaseFragmentWidzard;
import com.omise.assignment.charity.CharityModel;
import com.omise.assignment.databinding.FragmentDetailBinding;
import com.omise.assignment.donate.DonateActivity;

public class DetailFragment extends BaseFragmentWidzard {
	
	CharityModel mCharityModel;
	FragmentDetailBinding mBinding;
	@Override
	protected int getResView() {
		return R.layout.fragment_detail;
	}
	
	@Override
	protected String getFragmentName() {
		return "Detail";
	}
	
	@Override
	protected void injectDependencies() {
		
	}
	
	public static DetailFragment newInstace(Bundle bundle){
		DetailFragment fragment = new DetailFragment();
		if(bundle!=null){
			fragment.setArguments(bundle);
		}
		return fragment;
	}
	
	@Override
	protected void bindData(final ViewDataBinding binding) {
		mBinding = (FragmentDetailBinding) binding;
		mCharityModel = getArguments().getParcelable(DonateActivity.CHARITY_KEY);
		mBinding.setItem(mCharityModel);
	}
	
	@Override
	public boolean isAllowNext() {
		if(TextUtils.isEmpty(mBinding.etDonateValue.getText().toString())){
			mBinding.etDonateValue.setError("please fill donate amount");
			return false;
		}
		double donateValue = Double.parseDouble(mBinding.etDonateValue.getText().toString());
		if(donateValue < 20){
			mBinding.etDonateValue.setError("amount need to greater that 20");
			return false;
		}
		if(donateValue > 1000000){
			mBinding.etDonateValue.setError("amount need to lower that 1000000");
			return false;
		}
		return true;
	}
	
	public String getAmount(){
		return mBinding.etDonateValue.getText().toString();
	}
}
