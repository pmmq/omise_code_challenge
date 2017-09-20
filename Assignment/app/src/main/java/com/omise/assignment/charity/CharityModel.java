package com.omise.assignment.charity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.omise.assignment.applications.bases.BaseResponseModel;

public class CharityModel extends BaseResponseModel implements Parcelable {
	
	@SerializedName("id")
	protected int id;
	@SerializedName("name")
	protected String name;
	@SerializedName("logo_url")
	protected String logoUrl;
	
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}
	
	public void setLogoUrl(final String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	@Override
	public int describeContents() { return 0; }
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.logoUrl);
	}
	
	public CharityModel() {}
	
	protected CharityModel(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.logoUrl = in.readString();
	}
	
	public static final Parcelable.Creator<CharityModel> CREATOR = new Parcelable.Creator<CharityModel>() {
		@Override
		public CharityModel createFromParcel(Parcel source) {return new CharityModel(source);}
		
		@Override
		public CharityModel[] newArray(int size) {return new CharityModel[size];}
	};
}
