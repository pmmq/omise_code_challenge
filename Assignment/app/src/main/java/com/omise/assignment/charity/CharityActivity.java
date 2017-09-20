package com.omise.assignment.charity;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import co.omise.android.models.Token;
import co.omise.android.ui.CreditCardActivity;
import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.applications.components.ApplicationComponent;
import com.omise.assignment.applications.interfaces.AdapterContract;
import com.omise.assignment.databinding.ActivityCharityBinding;
import com.omise.assignment.donate.DonateActivity;
import org.greenrobot.eventbus.EventBus;

public class CharityActivity extends BaseActivity implements CharityContract.View,AdapterContract<CharityModel>{
	
	private static final int REQUEST_CC = 100;
	
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
		if(this.isLoading == null){
			this.isLoading = new ObservableBoolean(isLoading);
		}
		this.isLoading.set(isLoading);
	}
	
	@Override
	protected void bindData(ViewDataBinding binding) {
		mActivityMainBinding = (ActivityCharityBinding) binding;
		mActivityMainBinding.setActivity(this);
	}
	
	private void showCreditCardForm() {
		Intent intent = new Intent(this, CreditCardActivity.class);
		intent.putExtra(CreditCardActivity.EXTRA_PKEY, getResources().getString(R.string.omise_pkey));
		startActivityForResult(intent, REQUEST_CC);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CC:
				if (resultCode == CreditCardActivity.RESULT_CANCEL) {
					return;
				}
				
				Token token = data.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT);
				// TODO: 20/09/2017 call api to get token
			
			default:
				super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	public void onItemSelect(final CharityModel model) {
		Intent intent = new Intent(this, DonateActivity.class);
		intent.putExtra(DonateActivity.CHARITY_KEY,model);
		startActivity(intent);
	}
	
}
