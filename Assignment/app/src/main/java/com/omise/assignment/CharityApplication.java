package com.omise.assignment;

import android.app.Application;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.omise.assignment.applications.components.ApplicationComponent;
import com.omise.assignment.applications.components.DaggerApplicationComponent;
import com.omise.assignment.applications.modules.ApplicationModule;
import com.omise.assignment.applications.modules.ServiceModule;


public class CharityApplication extends Application {
	
	private static Application mApplication;
	private ApplicationComponent mApplicationComponent;
	
	@Override
	public void onCreate() {
		super.onCreate();
		XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
		mApplication = this;
		mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
				.serviceModule(new ServiceModule()).build();
	}
	
	public static Application getApplication() {
		return mApplication;
	}
	
	public ApplicationComponent getApplictionComponent() {
		return mApplicationComponent;
	}
}
