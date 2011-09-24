package com.hellbelk.client;

import com.hellbelk.client.worktime.WorkTimeWindow;
import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.core.client.EntryPoint;

public class WebDesktop implements EntryPoint{
	private Desktop desktop = new Desktop();
	
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
	}
}
