package com.omise.assignment;

import android.content.Intent;
import android.os.Bundle;
import javax.inject.Inject;

import co.omise.android.models.Token;
import co.omise.android.ui.CreditCardActivity;
import com.omise.assignment.applications.bases.BaseActivity;
import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity {
	
	private static final int REQUEST_CC = 100;
	
	@Inject
	EventBus mEventBus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean init(final Bundle saveInstanceState) {
		showCreditCardForm();
		return true;
	}
	
	@Override
	protected void injectDependencies() {
		((CharityApplication) getApplication()).getApplictionComponent().inject(this);
	}
	
	
	@Override
	protected int getResView() {
		return R.layout.activity_main;
	}
	
	private void showCreditCardForm() {
		Intent intent = new Intent(this, CreditCardActivity.class);
		intent.putExtra(CreditCardActivity.EXTRA_PKEY, getResources().getString(R.string.omise_pkey));
		startActivityForResult(intent, REQUEST_CC);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CC:
				if (resultCode == CreditCardActivity.RESULT_CANCEL) {
					return;
				}
				
				Token token = data.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT);
				// TODO: 20/09/2017 call api to get token
			
			default:
				super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
}
