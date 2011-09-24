package com.hellbelk.client.worktime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.HttpProxy;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.XmlLoadResultReader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.http.client.RequestBuilder;
import com.hellbelk.client.Strings;
import com.hellbelk.shared.Constants;

public class WorkTimeWindow extends Window{
	protected final String ACTION_TAG = "action";
	protected final String ADD_ACTION = "add";
	protected final String DELETE_ACTION = "delete";
	protected final String EDIT_ACTION = "edit";

	public WorkTimeWindow(){
		super();
		
		setMinimizable(true);
	    setMaximizable(true);
	    setHeading(Strings.WORK_TIME);
	    setWidth(460);
	    setHeight(350);
	    
	    setTopComponent(createControls());
	    add(createGrid());
	}
	
	protected ToolBar createControls(){
		SelectionListener<ButtonEvent> listener = new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(ce.getComponent().getData(ACTION_TAG).equals(ADD_ACTION)){
					new EditWork().show();
				}else if(ce.getComponent().getData(ACTION_TAG).equals(DELETE_ACTION)){
					MessageBox.info("Info", "Delete action", null);
				}else if(ce.getComponent().getData(ACTION_TAG).equals(EDIT_ACTION)){
					new EditWork(new Date(), new Date(), new Date(), "test").show();
				}
			}
			
		};
		
		ToolBar toolBar = new ToolBar();
	    Button item = new Button();
	    item.setIcon(IconHelper.createStyle("add_work_icon"));
	    item.setData(ACTION_TAG, ADD_ACTION);
	    item.addSelectionListener(listener);
	    toolBar.add(item);
	    
	    item = new Button();
	    item.setIcon(IconHelper.createStyle("delete_work_icon"));
	    item.setData(ACTION_TAG, DELETE_ACTION);
	    item.addSelectionListener(listener);
	    toolBar.add(item);

	    item = new Button();
	    item.setIcon(IconHelper.createStyle("edit_work_icon"));
	    item.setData(ACTION_TAG, EDIT_ACTION);
	    item.addSelectionListener(listener);
	    toolBar.add(item);
	    return toolBar;
	}
	
	protected Grid createGrid(){
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    columns.add(new ColumnConfig(Constants.DATE_TAG, Strings.DATE, 40));
	    columns.add(new ColumnConfig(Constants.START_TAG, Strings.START, 40));
	    columns.add(new ColumnConfig(Constants.BREAK_TAG, Strings.BREAK, 40));
	    columns.add(new ColumnConfig(Constants.END_TAG, Strings.END, 40));
	    columns.add(new ColumnConfig(Constants.COMMENT_TAG, Strings.COMMENT, 300));

	    ColumnModel cm = new ColumnModel(columns);

	    ModelType type = new ModelType();
	    type.setRoot(Constants.DAYS_TAG);
	    type.setRecordName(Constants.DAY_TAG);
	    type.addField(Constants.DATE_TAG);
	    type.addField(Constants.START_TAG);
	    type.addField(Constants.BREAK_TAG);
	    type.addField(Constants.END_TAG);
	    type.addField(Constants.COMMENT_TAG);

	    // Determine if Explorer or Example for XML path
//	    String path = GWT.getHostPageBaseURL() + (Examples.isExplorer() ? "" : "../../") + "data/data.xml";

	    // use a http proxy to get the data
//	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "");//TODO: add loading data
//	    HttpProxy<String> proxy = new HttpProxy<String>(builder);

	    // need a loader, proxy, and reader
//	    XmlLoadResultReader<ListLoadResult<ModelData>> reader = new XmlLoadResultReader<ListLoadResult<ModelData>>(type);

//	    final BaseListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy,
//	        reader);

//	    ListStore<ModelData> store = new ListStore<ModelData>(loader);
	    ListStore<ModelData> store = new ListStore<ModelData>();
	    final Grid<ModelData> grid = new Grid<ModelData>(store, cm);
	    grid.setBorders(true);
	    grid.setLoadMask(true);
	    return grid;
	}
}
