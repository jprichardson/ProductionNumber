package com.reflect7.productionnumber.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.productionnumber.shared.model.Task;

public class TaskItemView extends Composite {

	private static TaskItemViewUiBinder uiBinder = GWT
			.create(TaskItemViewUiBinder.class);

	interface TaskItemViewUiBinder extends UiBinder<Widget, TaskItemView> {
	}

	@UiField Label labelStartTime;
	@UiField Label labelEndTime;
	@UiField Label labelDescription;

	public TaskItemView(Task task) {
		initWidget(uiBinder.createAndBindUi(this));
		_task = task;
		
		labelStartTime.setText(task.getStartTimeString());
		labelDescription.setText(task.getDescription());
		
		if (_task.getStopTime() != null)
			labelEndTime.setText(task.getStopTimeString());
		
		String color = null;
		if (task.getTaskType() == Task.TaskType.Produce)
			color = "green";
		else
			color = "red";
			
		DOM.setStyleAttribute(this.getElement(), "color", color);
	}
	
	private Task _task = null;
	public Task getTask() { return _task; }
	
	public void setEndTime(String endTime){
		labelEndTime.setText(endTime);
	}
}
