package com.reflect7.productionnumber.client.view;


import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.reflect7.productionnumber.client.remoteservice.TaskService;
import com.reflect7.productionnumber.client.remoteservice.TaskServiceAsync;
import com.reflect7.productionnumber.shared.model.Day;
import com.reflect7.productionnumber.shared.model.Task;

public class TodayView extends Composite {

	private static TodayViewUiBinder uiBinder = GWT.create(TodayViewUiBinder.class);

	interface TodayViewUiBinder extends UiBinder<Widget, TodayView> {
	
	}

	@UiField Button buttonConsume;
	@UiField Button buttonProduce;
	@UiField TextBox textBoxDescription;
	@UiField VerticalPanel taskPanel;
	@UiField ScrollPanel scroller;
	
	@UiField Label labelIn;
	@UiField Label labelOut;
	@UiField Label labelNumber;
	
	private static TaskServiceAsync taskService = GWT.create(TaskService.class);
	
	private TaskItemView _currentTask = null;

	private Day _today = null;
	
	public TodayView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		taskService.getToday("", new AsyncCallback<Day>(){
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			public void onSuccess(Day result) {
				_today = result;
				
				for (Task t : _today.getTasks()){
					TaskItemView tiv = new TaskItemView(t);
					taskPanel.add(tiv);
				}
				
				short in = _today.getSumConsumptionMins();
				short out =  _today.getSumProductionMins();
				double num = 0.0;
				if (in != 0)
					num = (double)out / (double)in;
				
				labelIn.setText("In: " + in);
				labelOut.setText("Out: " + out);
				labelNumber.setText("Number:" + num);
			}
		});
		
		/*Window.addResizeHandler(new ResizeHandler(){
			public void onResize(ResizeEvent event) {
				Element e = scroller.getElement();
				int h = e.getAbsoluteBottom() - e.getAbsoluteTop();
				System.out.println(e.getClientHeight());
				System.out.println(h);
				//DOM.setStyleAttribute(scroller.getElement(), attr, value)
			}
		});
		
		final TodayView self = this;
		DeferredCommand.addCommand(new Command(){
			public void execute() {
				//self.onResize();
			}
		});*/
	}

	@UiHandler("buttonConsume")
	void onConsumeClick(ClickEvent e) {
		if (buttonConsume.getText().equals("Start Consuming")){
			buttonConsume.setText("Stop Consuming");
			buttonProduce.setEnabled(false);
			
			Task t = createTask(Task.TaskType.Consume, textBoxDescription.getText(), new Date());
			_currentTask = new TaskItemView(t);
			
			textBoxDescription.setText("");
			
			taskPanel.add(_currentTask);
			_today.getTasks().add(t);
		} else {
			_currentTask.getTask().setStopTime(new Date());
			_currentTask.setEndTime(_currentTask.getTask().getStopTimeString());
			
			taskService.saveDay(_today, new AsyncCallback<String>(){
				public void onFailure(Throwable caught) {
					Window.alert("fail");
				}

				public void onSuccess(String result) {
					
				}
			});
			
			buttonConsume.setText("Start Consuming");
			buttonProduce.setEnabled(true);
		}
	}
	
	@UiHandler("buttonProduce")
	void onProduceClick(ClickEvent e) {
		if (buttonProduce.getText().equals("Start Producing")){
			buttonProduce.setText("Stop Producing");
			buttonConsume.setEnabled(false);
			
			Task t = createTask(Task.TaskType.Produce, textBoxDescription.getText(), new Date());
			_currentTask = new TaskItemView(t);
			
			textBoxDescription.setText("");
			
			taskPanel.add(_currentTask);
			_today.getTasks().add(t);
		} else {
			_currentTask.getTask().setStopTime(new Date());
			_currentTask.setEndTime(_currentTask.getTask().getStopTimeString());
			
			taskService.saveDay(_today, new AsyncCallback<String>(){
				public void onFailure(Throwable caught) {
					Window.alert("fail");
				}

				public void onSuccess(String result) {
					
				}
			});
			
			buttonProduce.setText("Start Producing");
			buttonConsume.setEnabled(true);
		}
	}
	
	//@Override
	/*public void onResize(){
		
	}*/
	
	public Task createTask(Task.TaskType tt, String description, Date startTime){
		Task t = new Task();
		t.setTaskType(tt); t.setDescription(description); t.setStartTime(startTime);
		
		return t;
	}

	
}
