package com.omise.assignment.applications.modules;

import javax.inject.Singleton;

import com.omise.assignment.applications.managers.ApplicationManager;
import com.omise.assignment.TumBoonApplication;
import dagger.Module;
import dagger.Provides;

import org.greenrobot.eventbus.EventBus;

@Module
public class ApplicationModule {
	
	TumBoonApplication mApplication;
	EventBus mEventBus;
	
	public ApplicationModule(TumBoonApplication mApplication) {
		mApplication = mApplication;
	}
	
	@Provides
	@Singleton
	ApplicationManager provideApplicationManager(EventBus pEventBus) {
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
