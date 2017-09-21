package com.omise.assignment.applications.bases;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.annotation.Resource;
import javax.inject.Inject;

import com.omise.assignment.TumBoonService;

public abstract class BaseActivity extends AppCompatActivity {
	
	@Inject public TumBoonService mTumBoonService;
	
	public ObservableBoolean isLoading;
	
	private ViewDataBinding mBinding;
	
	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injectDependencies();
		isLoading = new ObservableBoolean(false);
		mBinding = DataBindingUtil.setContentView(this, getResView());
		bindData(mBinding);
		// setContentView(getResView());
		if (!init(savedInstanceState)) {
			
			Log.d("debug", "something went wrong");
			return;
		}
	}
	
	/**
	 * to attach fragment to view with option add to backstack
	 */
	protected FragmentTransaction attachFragment(Fragment fragment, int layout, boolean addToBackStack) {
		FragmentTransaction transaction = this.attachFragment(fragment, layout);
		if (addToBackStack) {
			transaction.addToBackStack(fragment.getClass().getName());
		}
		return transaction;
	}
	
	/**
	 * to attach fragment to view
	 */
	protected FragmentTransaction attachFragment(Fragment fragment, int layout) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(layout, fragment, fragment.getClass().getName());
		transaction.commitAllowingStateLoss();
		return transaction;
	}
	
	/**
	 * this will call automatically when activity cycle trigger oncreate
	 */
	protected abstract boolean init(Bundle saveInstanceState);
	
	/**
	 * to force implement inject depencies
	 */
	protected abstract void injectDependencies();
	
	/**
	 * for implement cast databind from param to current view
	 */
	protected abstract void bindData(ViewDataBinding mBinding);
	
	/**
	 * to provide view resorce to base class
	 */
	@Resource
	protected abstract int getResView();
}
