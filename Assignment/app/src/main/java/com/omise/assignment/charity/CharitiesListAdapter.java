package com.omise.assignment.charity;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.bumptech.glide.Glide;
import com.omise.assignment.R;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.interfaces.AdapterContract;
import com.omise.assignment.databinding.CharityItemBinding;

public class CharitiesListAdapter extends RecyclerView.Adapter<CharitiesListAdapter.CharityHolder> {
	
	List<CharityModel> mCharities;
	AdapterContract<CharityModel> mContract;
	
	public CharitiesListAdapter(final List<CharityModel> charities) {
		mCharities = charities;
	}
	
	@Override
	public CharityHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		CharityItemBinding binding = CharityItemBinding.inflate(layoutInflater,parent,false);
		CharityHolder charityHolder = new CharityHolder(binding);
		return charityHolder;
	}
	
	@Override
	public void onBindViewHolder(final CharityHolder holder, final int position) {
		holder.bind(mCharities.get(position),this,position);
	}
	
	@Override
	public int getItemCount() {
		return mCharities.size();
	}
	
	public int getColor(int position){
		if(position%2 == 0){
			return TumBoonApplication.getApplication().getResources().getColor(R.color.transparent_white_percent_35);
		}else{
			return TumBoonApplication.getApplication().getResources().getColor(R.color.transparent_white_percent_15);
		}
	}
	
	public class CharityHolder extends RecyclerView.ViewHolder {
		
		public CharityItemBinding mBinding;
		
		public CharityHolder(CharityItemBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}
		
		public void bind(CharityModel charity, CharitiesListAdapter adapter,int position) {
			mBinding.setItem(charity);
			mBinding.setAdapter(adapter);
			mBinding.setPosition(position);
			mBinding.executePendingBindings();
		}
	}
	
	public void onSelectItem(View view, CharityModel model) {
		if (mContract != null) {
			mContract.onItemSelect(model);
		}
	}
	
	public void setContract(final AdapterContract<CharityModel> contract) {
		mContract = contract;
	}
	
}



