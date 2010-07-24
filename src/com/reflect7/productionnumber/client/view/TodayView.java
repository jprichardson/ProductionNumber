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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.reflect7.productionnumber.client.remoteservice.TaskService;
import com.reflect7.productionnumber.client.remoteservice.TaskServiceAsync;
import com.reflect7.productionnumber.shared.lib.util.MathUtil;
import com.reflect7.productionnumber.shared.model.Day;
import com.reflect7.productionnumber.shared.model.Task;
import com.reflect7.productionnumber.shared.model.Task.TaskType;

public class TodayView extends Composite {

	private static TodayViewUiBinder uiBinder = GWT.create(TodayViewUiBinder.class);

	interface TodayViewUiBinder extends UiBinder<Widget, TodayView> {
	
	}

	@UiField Button buttonConsume;
	@UiField Button buttonProduce;
	@UiField TextBox textBoxDescription;
	@UiField VerticalPanel taskPanel;
	@UiField VerticalPanel panelDay;
	@UiField ScrollPanel scroller;
	
	@UiField Button buttonAddTask;
	@UiField TextBox textBoxAddDescription;
	@UiField TextBox textBoxStartTime;
	@UiField TextBox textBoxEndTime;
	@UiField RadioButton radioConsume;
	@UiField RadioButton radioProduce;
	
	
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
				Window.alert("fail load today");
			}

			public void onSuccess(Day result) {
				_today = result;
				
				for (Task t : _today.getTasks()){
					TaskItemView tiv = new TaskItemView(t);
					taskPanel.add(tiv);
				}
				
				float in = _today.getSumConsumptionMins();
				float out =  _today.getSumProductionMins();
				float num = 0.0f;
				if (in != 0)
					num = out / in;
				in = MathUtil.round(in, 2);
				out = MathUtil.round(out, 2);
				num = MathUtil.round(num, 2);
				
				labelIn.setText("In: " + in);
				labelOut.setText("Out: " + out);
				labelNumber.setText("Number:" + num);
				
				taskService.getDays("", new AsyncCallback<Iterable<Day>>(){
					public void onFailure(Throwable caught) {
						Window.alert("fail load days");
					}

					public void onSuccess(Iterable<Day> result) {
						for (Day d : result){
							TodayItemView tdv = new TodayItemView(d);
							panelDay.add(tdv);
						}
					}
				});
			}
		});
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
					Window.alert("fail consume");
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
					Window.alert("fail produce");
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
	
	@UiHandler("buttonAddTask")
	void onAddTaskClick(ClickEvent ce){
		//Date start = new Date();
		//Date end = new Date();
		
		//clearsHoursMinSecs()
		try {
		
			String todayString = _today.getDateString();
			String startString = todayString + " " + textBoxStartTime.getText();
			String endString = todayString + " " + textBoxEndTime.getText();
			
			DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd h:mm a");
			
			Date start = dtf.parse(startString);
			Date end = dtf.parse(endString);
			
			if (end.getTime() - start.getTime() < 0){
				end.setDate(end.getDate() + 1);
			}
			
			Task t = new Task();
			if (radioConsume.isChecked())
				t.setTaskType(TaskType.Consume);
			
			if (radioProduce.isChecked())
				t.setTaskType(TaskType.Produce);
			
			t.setStartTime(start);
			t.setStopTime(end);
			
			t.setDescription(textBoxAddDescription.getText());
			
			TaskItemView tiv = new TaskItemView(t);
			
			taskPanel.add(tiv);
			
			_today.getTasks().add(t);
			
			taskService.saveDay(_today, new AsyncCallback<String>(){
				public void onFailure(Throwable caught) {
					Window.alert("fail add task");
				}

				public void onSuccess(String result) {
					textBoxStartTime.setText("");
					textBoxEndTime.setText("");
					textBoxAddDescription.setText("");
				}
			});
				
		}
		catch (Exception ex){
			Window.alert(ex.getMessage());
		}
	}
	
	public Task createTask(Task.TaskType tt, String description, Date startTime){
		Task t = new Task();
		t.setTaskType(tt); t.setDescription(description); t.setStartTime(startTime);
		
		return t;
	}

	
	@SuppressWarnings("deprecation")
	private void clearHoursMinsSecs(Date d){
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
	}
	
}
