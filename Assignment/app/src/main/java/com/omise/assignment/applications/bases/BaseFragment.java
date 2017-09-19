package com.omise.assignment.applications.bases;

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
	
	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		injectDependencies();
		ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(getFragmentName());
		}
	}
	
	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		View rootView = inflater.inflate(getResView(),container,false);
		return rootView;
	}
	
	
	@Resource
	protected abstract int getResView();
	
	protected abstract String getFragmentName();
	
	protected abstract void injectDependencies();
}
