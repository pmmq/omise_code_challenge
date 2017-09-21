package com.omise.assignment.applications.bases;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.annotation.Resource;

public abstract class BaseFragment extends Fragment {
	
	boolean isViewShowing;
	
	private ViewDataBinding mBinding;
	
	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		setUserVisibleHint(false);
		super.onCreate(savedInstanceState);
		injectDependencies();
	}
	
	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		View rootView = inflater.inflate(getResView(), container, false);
		bindData(DataBindingUtil.bind(rootView));
		if(isViewShowing){
			setActionBar();
		}
		return rootView;
	}
	
	@Override
	public void setMenuVisibility(final boolean visible) {
		super.setMenuVisibility(visible);
		if (visible) {
			setActionBar();
		}
		isViewShowing = visible;
	}
	
	private void setActionBar() {
		if (getActivity() != null) {
			ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
			if (actionBar != null) {
				actionBar.setTitle(getFragmentName());
			}
		}
	}
	
	/**
	 * for implement cast databind from param to current view
	 */
	protected abstract void bindData(ViewDataBinding binding);
	
	/**
	 * to provide layout resource to base class to automatic load
	 */
	@Resource
	protected abstract int getResView();
	
	/**
	 * to provide framgent name to base class
	 */
	protected abstract String getFragmentName();
	
	/**
	 * to inject dependencies
	 */
	protected abstract void injectDependencies();
}
