package com.reflect7.productionnumber.client;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.*;

public class MyPlaceManager /*extends PlaceManagerImpl*/ {

	//private final Proxy<?> defaultProxy;

	@Inject
	/*public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, MainPagePresenter.MyProxy defaultProxy) {
		super(eventBus, tokenFormatter);
		this.defaultProxy = defaultProxy;
	}*/

	//@Override
	public void revealDefaultPlace() {
		//defaultProxy.reveal();
	}
}