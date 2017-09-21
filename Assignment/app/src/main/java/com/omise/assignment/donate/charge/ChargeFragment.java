package com.omise.assignment.donate.charge;

import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import co.omise.android.CardNumber;
import co.omise.android.TokenRequest;
import co.omise.android.models.CardBrand;
import co.omise.android.ui.CreditCardActivity;
import com.omise.assignment.R;
import com.omise.assignment.applications.bases.BaseFragment;
import com.omise.assignment.applications.bases.BaseFragmentWidzard;
import com.omise.assignment.databinding.FragmentChargeBinding;

public class ChargeFragment extends BaseFragmentWidzard {
	
	FragmentChargeBinding mBinding;
	
	@Override
	protected int getResView() {
		return R.layout.fragment_charge;
	}
	
	@Override
	protected String getFragmentName() {
		return "Charge";
	}
	
	@Override
	protected void injectDependencies() {
		
	}
	
	public static ChargeFragment newInstace(Bundle bundle){
		ChargeFragment fragment = new ChargeFragment();
		if(bundle!=null){
			fragment.setArguments(bundle);
		}
		return fragment;
	}
	
	@Override
	protected void bindData(final ViewDataBinding binding) {
		mBinding = (FragmentChargeBinding) binding;
		mBinding.spinnerExpiryMonth.setAdapter(new ExpiryMonthSpinnerAdapter());
		mBinding.spinnerExpiryYear.setAdapter(new ExpiryYearSpinnerAdapter());
		mBinding.textCardNumber.addTextChangedListener(new ChargeTextWatcher());
	}
	
	@Override
	public boolean isAllowNext() {
		boolean valid = this.validateNonEmpty(mBinding.editCardName)
				& this.validateNonEmpty(mBinding.editCardNumber)
				& this.validateNonEmpty(mBinding.editCardNumber)
				& this.validateNonEmpty(mBinding.editSecurityCode)
				& this.validateLuhn(mBinding.editCardNumber);
		if(valid){
			return true;
		}
		return  false;
	}
	
	private boolean validateNonEmpty(EditText field) {
		String value = field.getText().toString().trim();
		if(value.isEmpty()) {
			field.setError(String.format(this.getString(co.omise.android.R.string.error_required), new Object[]{field.getHint()}));
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLuhn(EditText field) {
		String value = field.getText().toString().trim();
		if(!CardNumber.luhn(value)) {
			field.setError(String.format(this.getString(co.omise.android.R.string.error_invalid), new Object[]{field.getHint()}));
			return false;
		} else {
			return true;
		}
	}
	
	private class ChargeTextWatcher implements TextWatcher {
		private ChargeTextWatcher() {
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		public void afterTextChanged(Editable s) {
			String pan = s.toString();
			if(pan.length() > 6) {
				CardBrand brand = CardNumber.brand(pan);
				if(brand != null && brand.getLogoResourceId() > -1) {
					mBinding.imageCardBrand.setImageResource(brand.getLogoResourceId());
					return;
				}
			}
			mBinding.imageCardBrand.setImageDrawable((Drawable)null);
		}
	}
	
	public TokenRequest getTokenRequest(){
		TokenRequest request = new TokenRequest();
		request.number = mBinding.editCardNumber.getText().toString();
		request.name = mBinding.editCardName.getText().toString();
		request.expirationMonth = ((Integer)mBinding.spinnerExpiryMonth.getSelectedItem()).intValue();
		request.expirationYear = ((Integer)mBinding.spinnerExpiryYear.getSelectedItem()).intValue();
		request.securityCode = mBinding.editSecurityCode.getText().toString();
		return request;
	}
}
