package com.omise.assignment.applications.events;


public class NetworkStateEvent {
	private boolean isConnect;
	private String networkMethod;
	
	public NetworkStateEvent(boolean isConnect, String networkMethod) {
		this.isConnect = isConnect;
		this.networkMethod = networkMethod;
	}
	
	public String getNetworkMethod() {
		return networkMethod;
	}
	
	public boolean isConnect() {
		
		return isConnect;
	}
	
	public static String WIFI = "WIFI";
	public static String MOBILE = "MOBILE";
}
