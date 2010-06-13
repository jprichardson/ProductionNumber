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

public class TaskItemView extends Composite {

	private static TaskItemViewUiBinder uiBinder = GWT
			.create(TaskItemViewUiBinder.class);

	interface TaskItemViewUiBinder extends UiBinder<Widget, TaskItemView> {
	}

	@UiField Label labelStartTime;
	@UiField Label labelEndTime;
	@UiField Label labelDescription;
	
	private boolean _isProducing = false;

	public TaskItemView(String startTime, String description, boolean isProducing) {
		initWidget(uiBinder.createAndBindUi(this));
		labelStartTime.setText(startTime);
		labelDescription.setText(description);
	}
	
	public void setEndTime(String endTime){
		labelEndTime.setText(endTime);
	}
}
