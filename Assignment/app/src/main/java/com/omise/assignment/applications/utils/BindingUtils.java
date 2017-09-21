package com.omise.assignment.applications.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

public class BindingUtils {
	
	@BindingAdapter("imageUrl")
	public static void setImageUrl(AppCompatImageView imageView, String url) {
		Context context = imageView.getContext();
		Glide.with(context).load(url).into(imageView);
	}
	
}
