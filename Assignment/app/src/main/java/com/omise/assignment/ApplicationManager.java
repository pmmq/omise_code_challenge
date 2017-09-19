package com.omise.assignment;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

public class ApplicationManager {
	
	private CharityApplication mApplication;
	private EventBus mEventBus;
	
	public ApplicationManager(final CharityApplication pApplication, final EventBus pEventBus) {
		mApplication = pApplication;
		mEventBus = pEventBus;
	}
}
