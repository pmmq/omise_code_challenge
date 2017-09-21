package com.omise.assignment.donate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import co.omise.android.TokenRequest;
import co.omise.android.models.Token;
import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.applications.bases.BaseFragment;
import com.omise.assignment.applications.bases.BaseFragmentWidzard;
import com.omise.assignment.applications.components.ApplicationComponent;
import com.omise.assignment.applications.events.AmountChangeEvent;
import com.omise.assignment.charity.CharityModel;
import com.omise.assignment.databinding.ActivityDonateBinding;
import com.omise.assignment.donate.charge.ChargeFragment;
import com.omise.assignment.donate.detail.DetailFragment;
import com.omise.assignment.donate.summary.SummarytFragment;
import org.greenrobot.eventbus.EventBus;

public class DonateActivity extends BaseActivity implements DonateContract.View {
	
	
	public static final int RESULT_OK = 100;
	public static final int RESULT_CANCEL = 200;
	public static final String CHARITY_KEY = "CHARITY_KEY";
	CharityModel mCharityModel;
	DonateFragmentAdapter mAdapter;
	ActivityDonateBinding mDonateBinding;
	List<BaseFragment> mFragmentList;
	DonatePresenter mPresenter;
	Token mToken;
	TokenRequest mTokenRequest;
	String mAmount;
	
	@Inject EventBus mEventBus;
	
	@Override
	protected boolean init(final Bundle saveInstanceState) {
		mPresenter = new DonatePresenter(this);
		mCharityModel = getIntent().getExtras().getParcelable(CHARITY_KEY);
		buildFragmentList();
		mAdapter = new DonateFragmentAdapter(getSupportFragmentManager(),mFragmentList);
		mDonateBinding.pager.setAdapter(mAdapter);
		mDonateBinding.pager.setOffscreenPageLimit(3);
		mDonateBinding.indicator.setViewPager(mDonateBinding.pager, mDonateBinding.pager.getAdapter().getCount());
		// prevent manual swipe
		mDonateBinding.pager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent motionEvent) {
				return true;
			}
		});
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
	
	@Override
	public ApplicationComponent getAppComponent() {
		return TumBoonApplication.getApplication().getApplictionComponent();
	}
	
	@Override
	public void onLoading(final boolean isLoading) {
		this.isLoading.set(isLoading);
	}
	
	@Override
	public void onReceiveToken(final Token token) {
		if(token != null){
			mToken = token;
			mPresenter.makeDonate(mCharityModel.getId(),mToken,mTokenRequest.name,mAmount);
		}else{
			Toast.makeText(this,"failed to register card number please check your credit card",Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onSuccessDonate(final DonateModel donateModel) {
		if(donateModel == null){
			Toast.makeText(this,"failed to register card number please check your credit card",Toast.LENGTH_LONG).show();
		}else if(donateModel.isError()){
			Toast.makeText(this, donateModel.getError() ,Toast.LENGTH_LONG).show();
		}else {
			if (mDonateBinding.pager.getCurrentItem() + 1 < mDonateBinding.pager.getAdapter().getCount()) {
				mDonateBinding.pager.setCurrentItem(mDonateBinding.pager.getCurrentItem() + 1);
			}
		}
	}
	
	/**
	 * trigger when user click back from view ,
	 * when view pager is 0 mean user stay in the first page then show dialog and finish activity
	 *
	 * @param v current trigger view
	 */
	public void onBackPress(View v) {
		if(mFragmentList.get(mDonateBinding.pager.getCurrentItem()) instanceof SummarytFragment){
			Toast.makeText(this,"not allow to back",Toast.LENGTH_SHORT).show();
			return;
		}
		if (mDonateBinding.pager.getCurrentItem() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("do you want to back to home ?");
			builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, final int i) {
					Intent data = new Intent();
					setResult(RESULT_CANCELED,data);
					finish();
				}
			});
			builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialogInterface, final int i) {
					dialogInterface.dismiss();
				}
			});
			builder.show();
		} else {
			mDonateBinding.pager.setCurrentItem(mDonateBinding.pager.getCurrentItem() - 1);
		}
	}
	
	/**
	 * trigger when user check next
	 *
	 * @param v current trigger view
	 */
	public void onNextPress(View v) {
		if( ((BaseFragmentWidzard)mFragmentList.get(mDonateBinding.pager.getCurrentItem())).isAllowNext()){
			if(mFragmentList.get(mDonateBinding.pager.getCurrentItem()) instanceof SummarytFragment){
				Intent data = new Intent();
				setResult(RESULT_OK,data);
				finish();
			}
			if(mFragmentList.get(mDonateBinding.pager.getCurrentItem()) instanceof DetailFragment){
				mAmount = ((DetailFragment)mFragmentList.get(mDonateBinding.pager.getCurrentItem())).getAmount();
				mEventBus.post(new AmountChangeEvent(mAmount));
			}
			if(mFragmentList.get(mDonateBinding.pager.getCurrentItem()) instanceof ChargeFragment){
				mTokenRequest = ((ChargeFragment)mFragmentList.get(mDonateBinding.pager.getCurrentItem())).getTokenRequest();
				mPresenter.requestToken(mTokenRequest);
			}else {
				if (mDonateBinding.pager.getCurrentItem() + 1 < mDonateBinding.pager.getAdapter().getCount()) {
					mDonateBinding.pager.setCurrentItem(mDonateBinding.pager.getCurrentItem() + 1);
				}
			}
		}
	}
	
	private List<BaseFragment> buildFragmentList(){
		if(mFragmentList == null){
			mFragmentList = new LinkedList<>();
		}
		Bundle DetailBundle = new Bundle();
		DetailBundle.putParcelable(CHARITY_KEY,mCharityModel);
		mFragmentList.add(DetailFragment.newInstace(DetailBundle));
		mFragmentList.add(ChargeFragment.newInstace(null));
		mFragmentList.add(SummarytFragment.newInstace(null));
		return mFragmentList;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.onDetach();
	}
}
