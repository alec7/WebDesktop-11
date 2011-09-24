package com.hellbelk.client.worktime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelComparer;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.hellbelk.client.Strings;
import com.hellbelk.shared.Constants;

public class WorkTimeWindow extends Window{
	protected final String ACTION_TAG = "action";
	protected final String ADD_ACTION = "add";
	protected final String DELETE_ACTION = "delete";
	protected final String EDIT_ACTION = "edit";
	
	Grid<ModelData> grid;
	Button addBt;
	Button deleteBt;
	Button editBt;
	
	public WorkTimeWindow(){
		super();
		
		setMinimizable(true);
	    setMaximizable(true);
	    setHeading(Strings.WORK_TIME);
	    setWidth(460);
	    setHeight(350);
	    setLayout(new BorderLayout());
	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
	    
	    setTopComponent(createControls());
	    
	    Grid<ModelData> workGrid = createGrid();
	    
//	    workGrid.addListener(Events.SelectionChange, new Listener<SelectionChangedEvent<ModelData>>() {
//
//			@Override
//			public void handleEvent(SelectionChangedEvent<ModelData> be) {
//				if(be == null)
//					MessageBox.alert("Warnint", "Selection is null", null);
//				deleteBt.setEnabled(be.getSelection().size() > 0);
//				editBt.setEnabled(be.getSelection().size() == 1);			
//			}});
	    
	    add(workGrid, data);
	}
	
	protected ToolBar createControls(){
		SelectionListener<ButtonEvent> listener = new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(ce.getComponent().getData(ACTION_TAG).equals(ADD_ACTION)){
					new EditWork(WorkTimeWindow.this).show();					
				}else if(ce.getComponent().getData(ACTION_TAG).equals(DELETE_ACTION)){
					for(ModelData data : grid.getSelectionModel().getSelection()){
						grid.getStore().remove(data);
					}
					//TODO: add delete callback 
				}else if(ce.getComponent().getData(ACTION_TAG).equals(EDIT_ACTION)){
					new EditWork(WorkTimeWindow.this, grid.getSelectionModel().getSelectedItem()).show();
				}
			}
			
		};
		
		ToolBar toolBar = new ToolBar();
		addBt = new Button();
	    addBt.setIcon(IconHelper.createStyle("add_work_icon"));
	    addBt.setData(ACTION_TAG, ADD_ACTION);
	    addBt.addSelectionListener(listener);
	    toolBar.add(addBt);
	    
	    deleteBt = new Button();
	    deleteBt.setIcon(IconHelper.createStyle("delete_work_icon"));
	    deleteBt.setData(ACTION_TAG, DELETE_ACTION);
	    deleteBt.addSelectionListener(listener);
	    deleteBt.setEnabled(false);
	    toolBar.add(deleteBt);

	    editBt = new Button();
	    editBt.setIcon(IconHelper.createStyle("edit_work_icon"));
	    editBt.setData(ACTION_TAG, EDIT_ACTION);
	    editBt.addSelectionListener(listener);
	    editBt.setEnabled(false);
	    toolBar.add(editBt);
	    return toolBar;
	}
	
	protected Grid<ModelData> createGrid(){
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
	    type.addField(Constants.DATE_TAG, "@"+Constants.DATE_TAG);
	    type.addField(Constants.START_TAG, "@"+Constants.START_TAG);
	    type.addField(Constants.BREAK_TAG, "@"+Constants.BREAK_TAG);
	    type.addField(Constants.END_TAG, "@"+Constants.END_TAG);
	    type.addField(Constants.COMMENT_TAG, "@"+Constants.COMMENT_TAG);
	        

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
	    store.setModelComparer(new ModelComparer<ModelData>() {
			
			@Override
			public boolean equals(ModelData m1, ModelData m2) {
				return m1.get(Constants.DATE_TAG).equals(m2.get(Constants.DATE_TAG));				
			}
		});

//fake data
	    List<ModelData> works = new ArrayList<ModelData>();
	    for(int i = 0; i < 10; i++){
	    	works.add(new Work("1","1","1","1","work" + i));
	    }
	    store.add(works);
//fake data end

	    
	    grid = new Grid<ModelData>(store, cm);    
	    grid.setBorders(true);	
	    grid.getSelectionModel().setSelectionMode(SelectionMode.MULTI);	    
	    return grid;
	}
	
	public void addWork(ModelData data){
		grid.getStore().add(data);
	}
	
	public void modifyWork(ModelData data){
		grid.getStore().update(data);
	}

	public static class Work extends BaseModel{
		public Work(){
			
		}
		
		public Work(String date, String start, String _break, String end, String comment){
			setDay(date);
			setStart(start);
			setBreak(_break);			
			setEnd(end);
			setComment(comment);
		}
		
		public void setDay(String date){
			set(Constants.DATE_TAG, date);
		}
		
		public void setStart(String start){
			set(Constants.START_TAG, start);
		}
		
		public void setBreak(String _break){
			set(Constants.BREAK_TAG, _break);
		}
		
		public void setEnd(String end){
			set(Constants.END_TAG, end);
		}
		
		public void setComment(String comment){
			set(Constants.COMMENT_TAG, comment);
		}
		
		public String getDay(){
			return (String)get(Constants.DATE_TAG);
		}
		
		public String getStart(){
			return (String)get(Constants.START_TAG);
		}
		
		public String getBreak(){
			return (String)get(Constants.BREAK_TAG);
		}
		
		public String getEnd(){
			return (String)get(Constants.END_TAG);
		}
		
		public String getComment(){
			return (String)get(Constants.COMMENT_TAG);
		}
	}
}
