package com.omise.assignment.donate;

import com.google.gson.annotations.SerializedName;
import com.omise.assignment.applications.bases.BaseResponseModel;

public class DonateModel extends BaseResponseModel{
	@SerializedName("success")
	boolean success;
	
	public boolean isSuccess() {
		return success;
	}
}
