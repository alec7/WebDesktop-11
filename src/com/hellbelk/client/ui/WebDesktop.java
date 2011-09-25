package com.hellbelk.client.ui;

import com.hellbelk.client.Strings;
import com.hellbelk.client.bean.Worker;
import com.hellbelk.client.service.WorkService;
import com.hellbelk.client.service.WorkServiceAsync;
import com.hellbelk.client.service.WorkerService;
import com.hellbelk.client.service.WorkerServiceAsync;
import com.hellbelk.client.ui.worker.SelectWorkerWindow;
import com.hellbelk.client.ui.worktime.WorkTimeWindow;
import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class WebDesktop implements EntryPoint{
	public static final String WORK_SERVICE = GWT.getModuleBaseURL() + "work_service";
	public static final String WORKER_SERVICE = GWT.getModuleBaseURL() + "worker_service";
	
	private Desktop desktop = new Desktop();
	public static Worker currentWorker;
	
	private void itemSelected(ComponentEvent ce) {
	    Window w;
	    if (ce instanceof MenuEvent) {
	      MenuEvent me = (MenuEvent) ce;
	      w = me.getItem().getData("window");
	    } else {
	      w = ce.getComponent().getData("window");
	    }
	    if (!desktop.getWindows().contains(w)) {
	      desktop.addWindow(w);
	    }
	    if (w != null && !w.isVisible()) {
	      w.show();
	    } else {
	      w.toFront();
	    }
	  }
	
	public void onModuleLoad() {
		WorkServiceAsync workService = (WorkServiceAsync) GWT.create(WorkService.class);
		Registry.register(WORK_SERVICE, workService);
		
		WorkerServiceAsync workerService = (WorkerServiceAsync) GWT.create(WorkerService.class);
		Registry.register(WORKER_SERVICE, workerService);
		
		SelectionListener<ComponentEvent> shortcutListener = new SelectionListener<ComponentEvent>() {
		      @Override
		      public void componentSelected(ComponentEvent ce) {
		        itemSelected(ce);
		      }
		    };
		
		Window workTimeWindow = new WorkTimeWindow();
		
		Shortcut workTimeSh = new Shortcut();
		workTimeSh.setText(Strings.WORK_TIME);
		workTimeSh.setId("work-time-watch-shortcut");		
		workTimeSh.setData("window", workTimeWindow);
		workTimeSh.addSelectionListener(shortcutListener);
	    desktop.addShortcut(workTimeSh);
	    
	    Window selectWorker = new SelectWorkerWindow();
	    selectWorker.show();
	}
}
