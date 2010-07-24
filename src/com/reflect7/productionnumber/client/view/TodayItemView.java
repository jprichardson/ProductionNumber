package com.reflect7.productionnumber.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.productionnumber.shared.lib.util.MathUtil;
import com.reflect7.productionnumber.shared.model.Day;

public class TodayItemView extends Composite {

	private static TodayItemViewUiBinder uiBinder = GWT
			.create(TodayItemViewUiBinder.class);

	interface TodayItemViewUiBinder extends UiBinder<Widget, TodayItemView> {
	}

	@UiField Label labelIn;
	@UiField Label labelOut;
	@UiField Label labelDate;
	@UiField Label labelNumber;
	

	public TodayItemView(Day day) {
		initWidget(uiBinder.createAndBindUi(this));
		
		float in = day.getSumConsumptionMins();
		float out = day.getSumProductionMins();
		float num = 0.0f;
		if (in != 0)
			num = out / in;
		in = MathUtil.round(in, 2);
		out = MathUtil.round(out, 2);
		num = MathUtil.round(num, 2);
		
		labelIn.setText("In: " + in);
		labelOut.setText("Out: " + out);
		labelNumber.setText("Number: " + num);
		labelDate.setText(day.getDateString());
	}

}
