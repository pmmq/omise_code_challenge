package com.omise.assignment.applications.components;

import javax.inject.Singleton;

import com.omise.assignment.applications.bases.BaseActivity;
import com.omise.assignment.applications.modules.ApplicationModule;
import com.omise.assignment.applications.modules.ServiceModule;
import com.omise.assignment.applications.services.ConnectionChangeReceiver;
import com.omise.assignment.charity.CharityActivity;
import com.omise.assignment.charity.CharityPresenter;
import com.omise.assignment.donate.DonateActivity;
import com.omise.assignment.donate.DonatePresenter;
import com.omise.assignment.donate.summary.SummarytFragment;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,ServiceModule.class})
public interface ApplicationComponent {
	// act
	void inject (BaseActivity baseActivity);
	void inject (CharityActivity charityActivity);
	void inject (DonateActivity donateActivity);
	
	void inject (SummarytFragment fragment);
	
	// presenter
	void inject (CharityPresenter presenter);
	void inject (DonatePresenter presenter);
	// receiver
	void inject (ConnectionChangeReceiver connectionChangeReceiver);
}
