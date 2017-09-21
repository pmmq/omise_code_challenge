package com.omise.assignment.donate.summary;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.MainThread;

import javax.inject.Inject;

import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.TumBoonService;
import com.omise.assignment.applications.bases.BaseFragment;
import com.omise.assignment.applications.bases.BaseFragmentWidzard;
import com.omise.assignment.applications.events.AmountChangeEvent;
import com.omise.assignment.databinding.FragmentSummaryBinding;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SummarytFragment extends BaseFragmentWidzard {
	
	FragmentSummaryBinding mBinding;
	
	@Inject EventBus mEventBus;
	
	@Override
	protected int getResView() {
		return R.layout.fragment_summary;
	}
	
	@Override
	protected String getFragmentName() {
		return "Summary";
	}
	
	@Override
	protected void injectDependencies() {
		TumBoonApplication.getApplication().getApplictionComponent().inject(this);
	}
	
	public static SummarytFragment newInstace(Bundle bundle){
		SummarytFragment fragment = new SummarytFragment();
		if(bundle!=null){
			fragment.setArguments(bundle);
		}
		return fragment;
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onAmountChange(AmountChangeEvent event) {
		mBinding.tvThankyou.setText(getString(R.string.donataion_thankyou_message,event.getAmount()));
	};
	
	@Override
	protected void bindData(final ViewDataBinding binding) {
		this.mBinding = (FragmentSummaryBinding) binding;
	}
	
	@Override
	public boolean isAllowNext() {
		return true;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mEventBus.register(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mEventBus.unregister(this);
	}
}
