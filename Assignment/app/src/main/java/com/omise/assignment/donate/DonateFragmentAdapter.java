package com.omise.assignment.donate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import com.omise.assignment.applications.bases.BaseFragment;
import com.omise.assignment.donate.charge.ChargeFragment;
import com.omise.assignment.donate.detail.DetailFragment;
import com.omise.assignment.donate.summary.SummarytFragment;

public class DonateFragmentAdapter extends FragmentStatePagerAdapter {
	
	List<BaseFragment> mFragments;
	
	public DonateFragmentAdapter(final FragmentManager fm, List<BaseFragment> fragments) {
		super(fm);
		mFragments = fragments;
	}
	
	@Override
	public Fragment getItem(final int position) {
		return mFragments.get(position);
	}
	
	@Override
	public int getCount() {
		return mFragments.size();
	}
	
}
