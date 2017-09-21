package com.omise.assignment.applications.events;

/**
 * Created by Pub on 22/09/2017.
 */

public class AmountChangeEvent {
	
	String amount;
	
	public AmountChangeEvent(final String amount) {
		this.amount = amount;
	}
	
	public String getAmount() {
		return amount;
	}
}
