package com.omise.assignment;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.omise.assignment.applications.components.ApplicationComponent;
import com.omise.assignment.applications.components.DaggerApplicationComponent;
import com.omise.assignment.applications.modules.ApplicationModule;
import com.omise.assignment.applications.modules.ServiceModule;
import com.omise.assignment.applications.services.ConnectionChangeReceiver;

public class TumBoonApplication extends Application {
	
	private static TumBoonApplication mApplication;
	private ApplicationComponent mApplicationComponent;
	
	@Override
	public void onCreate() {
		super.onCreate();
		XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
		mApplication = this;
		mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
				.serviceModule(new ServiceModule()).build();
		
		// register network receiver for android version > nugat
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(new ConnectionChangeReceiver(), intentFilter);
		}
	}
	
	public static TumBoonApplication getApplication() {
		return mApplication;
	}
	
	public ApplicationComponent getApplictionComponent() {
		return mApplicationComponent;
	}
}
