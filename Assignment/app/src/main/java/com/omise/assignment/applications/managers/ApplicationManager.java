package com.omise.assignment.applications.managers;

import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.utils.NetworkUtil;
import org.greenrobot.eventbus.EventBus;

public class ApplicationManager {
	
	private TumBoonApplication mApplication;
	private EventBus mEventBus;
	private boolean isNetworkConnected;
	
	public ApplicationManager(final TumBoonApplication pApplication, final EventBus pEventBus) {
		mApplication = pApplication;
		mEventBus = pEventBus;
		setNetworkConnection(NetworkUtil.isConnected(TumBoonApplication.getApplication()));
	}
	
	public boolean isNetworkConnected() {
		return isNetworkConnected;
	}
	
	public void setNetworkConnection(final boolean pNetworkConnected) {
		isNetworkConnected = pNetworkConnected;
	}
}
