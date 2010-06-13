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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TodayView extends Composite {

	private static TodayViewUiBinder uiBinder = GWT.create(TodayViewUiBinder.class);

	interface TodayViewUiBinder extends UiBinder<Widget, TodayView> {
	
	}

	@UiField Button consumeButton;
	@UiField Button produceButton;
	@UiField TextBox descriptionTextBox;
	@UiField VerticalPanel taskPanel;
	@UiField ScrollPanel scroller;
	
	private TaskItemView _currentTask = null;

	public TodayView() {
		initWidget(uiBinder.createAndBindUi(this));
		//button.setText(firstName);
		
		Window.addResizeHandler(new ResizeHandler(){
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
		});
	}

	@UiHandler("consumeButton")
	void onConsumeClick(ClickEvent e) {
		if (consumeButton.getText().equals("Start Consuming")){
			consumeButton.setText("Stop Consuming");
			produceButton.setEnabled(false);
			
			Date o = new Date();
			DateTimeFormat dtf = DateTimeFormat.getShortDateTimeFormat();
			
			_currentTask = new TaskItemView(dtf.format(o),  descriptionTextBox.getText(), false);
			
			taskPanel.add(_currentTask);
		} else {
			Date o = new Date();
			DateTimeFormat dtf = DateTimeFormat.getShortDateTimeFormat();
			
			_currentTask.setEndTime(dtf.format(o));
			
			consumeButton.setText("Start Consuming");
			produceButton.setEnabled(true);
		}
	}
	
	@UiHandler("produceButton")
	void onProduceClick(ClickEvent e) {
		if (produceButton.getText().equals("Start Producing")){
			produceButton.setText("Stop Producing");
			consumeButton.setEnabled(false);
			
			Date o = new Date();
			DateTimeFormat dtf = DateTimeFormat.getShortDateTimeFormat();
			
			_currentTask = new TaskItemView(dtf.format(o),  descriptionTextBox.getText(), true);
			
			taskPanel.add(_currentTask);
		} else {
			produceButton.setText("Start Producing");
			consumeButton.setEnabled(true);
		}
	}
	
	//@Override
	/*public void onResize(){
		
	}*/

	
	
}
