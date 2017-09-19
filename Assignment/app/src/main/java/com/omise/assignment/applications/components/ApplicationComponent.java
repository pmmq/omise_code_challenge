package com.omise.assignment.applications.components;

import javax.inject.Singleton;

import com.omise.assignment.MainActivity;
import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.applications.modules.ApplicationModule;
import com.omise.assignment.applications.modules.ServiceModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,ServiceModule.class})
public interface ApplicationComponent {
	void inject (MainActivity mainActivity);
	void inject (BaseActivity baseActivity);
}
