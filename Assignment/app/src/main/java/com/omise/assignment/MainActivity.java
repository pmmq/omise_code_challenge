package com.omise.assignment;

import android.os.Bundle;
import javax.inject.Inject;
import com.omise.assignment.applications.bases.BaseActivity;
import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity {
	
	@Inject
	EventBus mEventBus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean init(final Bundle saveInstanceState) {
		return false;
	}
	
	@Override
	protected void injectDependencies() {
		((CharityApplication) getApplication()).getApplictionComponent().inject(this);
	}
	
	
	@Override
	protected int getResView() {
		return R.layout.activity_main;
	}
	
}
