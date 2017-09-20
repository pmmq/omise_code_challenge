package com.omise.assignment.applications.bases;

import com.omise.assignment.applications.components.ApplicationComponent;

public interface IBaseView {
	ApplicationComponent getAppComponent();
	void onLoading(boolean isLoading);
}
