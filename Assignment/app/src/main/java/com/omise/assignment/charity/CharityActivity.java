package com.omise.assignment.charity;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import co.omise.android.models.Token;
import co.omise.android.ui.CreditCardActivity;
import com.bumptech.glide.Glide;
import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.applications.components.ApplicationComponent;
import com.omise.assignment.applications.interfaces.AdapterContract;
import com.omise.assignment.databinding.ActivityCharityBinding;
import com.omise.assignment.donate.DonateActivity;
import org.greenrobot.eventbus.EventBus;

public class CharityActivity extends BaseActivity implements CharityContract.View,AdapterContract<CharityModel>{
	
	private static final int REQUEST_DONATE = 100;
	
	@Inject
	EventBus mEventBus;
	CharityPresenter mPresenter;
	ActivityCharityBinding mActivityMainBinding;
	LinearLayoutManager mLinearLayoutManager;
	CharitiesListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean init(final Bundle saveInstanceState) {
		// showCreditCardForm();
		mPresenter = new CharityPresenter(this);
		mPresenter.fetchCharity();
		mLinearLayoutManager = new LinearLayoutManager(this);
		return true;
	}
	
	@Override
	protected void injectDependencies() {
		TumBoonApplication.getApplication().getApplictionComponent().inject(this);
	}
	
	
	@Override
	protected int getResView() {
		return R.layout.activity_charity;
	}
	
	@Override
	public ApplicationComponent getAppComponent() {
		return TumBoonApplication.getApplication().getApplictionComponent();
	}
	
	@Override
	public void onFetched(final List<CharityModel> charities) {
		if(charities != null){
			mActivityMainBinding.rvCharities.setLayoutManager(mLinearLayoutManager);
			mAdapter = new CharitiesListAdapter(charities);
			mAdapter.setContract(this);
			mActivityMainBinding.rvCharities.setAdapter(mAdapter);
		}
	}
	
	@Override
	public void onLoading(final boolean isLoading) {
		this.isLoading.set(isLoading);
	}
	
	@Override
	protected void bindData(ViewDataBinding binding) {
		mActivityMainBinding = (ActivityCharityBinding) binding;
		mActivityMainBinding.setActivity(this);
		Glide.with(this).load(R.raw.thankyou).into(mActivityMainBinding.ivThankyou);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_DONATE:
				if (resultCode == DonateActivity.RESULT_CANCEL) {
					return;
				}
				showThankyou();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	public void onItemSelect(final CharityModel model) {
		Intent intent = new Intent(this, DonateActivity.class);
		intent.putExtra(DonateActivity.CHARITY_KEY,model);
		startActivityForResult(intent, REQUEST_DONATE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.onDetach();
	}
	
	private void showThankyou(){
		mActivityMainBinding.ivThankyou.setVisibility(View.VISIBLE);
		Handler mHandler;
		Runnable mHandlerTask;
		mHandler = new Handler();
		mHandlerTask = new Runnable() {
			@Override
			public void run() {
				if(mActivityMainBinding != null){
					mActivityMainBinding.ivThankyou.setVisibility(View.GONE);
				}
			}
		};
		mHandler.postDelayed(mHandlerTask,1000);
	}
	
	
}
