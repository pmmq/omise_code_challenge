package com.omise.assignment.applications.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import javax.inject.Inject;
import com.omise.assignment.TumBoonApplication;
import com.omise.assignment.applications.events.NetworkStateEvent;
import com.omise.assignment.applications.managers.ApplicationManager;
import com.omise.assignment.applications.utils.NetworkUtil;
import org.greenrobot.eventbus.EventBus;

public class ConnectionChangeReceiver extends BroadcastReceiver {
	
	@Inject EventBus mEventBus;
	
	@Inject ApplicationManager mAppManager;
	
	@Override
	public void onReceive(final Context context, final Intent pIntent) {
		((TumBoonApplication) context.getApplicationContext()).getApplictionComponent().inject(this);
		if(NetworkUtil.isConnected(context)){
			
			if((NetworkUtil.isConnectedMobile(context) && !NetworkUtil.isConnectedWifi(context))
					|| (!NetworkUtil.isConnectedMobile(context) && NetworkUtil.isConnectedWifi(context))){
				if(!mAppManager.isNetworkConnected()){
					mAppManager.setNetworkConnection(true);
					mEventBus.post(new NetworkStateEvent(true,NetworkUtil.getNetworkInfo(context).getTypeName()));
					NetworkUtil.showConnectionMsg(TumBoonApplication.getApplication(),"online");
				}
			}
		}else {
			if(!NetworkUtil.isConnectedMobile(context) && !NetworkUtil.isConnectedWifi(context)){
				mAppManager.setNetworkConnection(false);
			}
			NetworkUtil.showConnectionMsg(TumBoonApplication.getApplication(),"offline");
		}
	}
}
