package com.omise.assignment.applications.modules;

import javax.inject.Singleton;

import com.omise.assignment.ApplicationManager;
import com.omise.assignment.CharityApplication;
import dagger.Module;
import dagger.Provides;

import org.greenrobot.eventbus.EventBus;

@Module
public class ApplicationModule {
	
	CharityApplication mApplication;
	
	EventBus mEventBus;
	
	
	public ApplicationModule(CharityApplication mApplication) {
		mApplication = mApplication;
	}
	
	@Provides
	@Singleton
	ApplicationManager providesVMManager(EventBus pEventBus) {
		return new ApplicationManager(mApplication, pEventBus);
	}
	
	@Provides
	@Singleton
	EventBus providesEventBus() {
		if (mEventBus == null) {
			mEventBus = EventBus.builder().build();
		}
		return mEventBus;
	}
}
