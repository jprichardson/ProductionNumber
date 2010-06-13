package com.reflect7.productionnumber.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.reflect7.productionnumber.client.view.TodayView;




public class ProductionNumber implements EntryPoint {


	public void onModuleLoad() {
		RootPanel.get("app").add(new TodayView());
	}
}
