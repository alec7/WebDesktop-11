package com.hellbelk.client.ui.worktime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelComparer;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.DateWrapper;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hellbelk.client.Constants;
import com.hellbelk.client.Strings;
import com.hellbelk.client.bean.Work;
import com.hellbelk.client.service.WorkServiceAsync;
import com.hellbelk.client.ui.WebDesktop;

public class WorkTimeWindow extends Window{
	protected final String ACTION_TAG = "action";
	protected final String ADD_ACTION = "add";
	protected final String DELETE_ACTION = "delete";
	protected final String EDIT_ACTION = "edit";
	
	final WorkServiceAsync service = (WorkServiceAsync) Registry.get(WebDesktop.WORK_SERVICE);
	
	Grid<BeanModel> grid;
	ListLoader<ListLoadResult<ModelData>> loader;
	Button addBt;
	Button deleteBt;
	Button editBt;
	
	public WorkTimeWindow(){
		super();
		
		setMinimizable(true);
	    setMaximizable(true);
	    setHeading(Strings.WORK_TIME);
//	    setWidth(460);
	    setHeight(350);
	    setLayout(new BorderLayout());
	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
	    
	    setTopComponent(createControls());
	    
	    Grid<BeanModel> workGrid = createGrid();
	    
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
					for(final BeanModel data : grid.getSelectionModel().getSelection()){
						service.removeWork((Work)data.getBean(), new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								MessageBox.alert("Error", "Can't remove work.", null);								
							}

							@Override
							public void onSuccess(Boolean result) {
								grid.getStore().remove(data);								
							}
						});
					}
					
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
//	    deleteBt.setEnabled(false);
	    toolBar.add(deleteBt);

	    editBt = new Button();
	    editBt.setIcon(IconHelper.createStyle("edit_work_icon"));
	    editBt.setData(ACTION_TAG, EDIT_ACTION);
	    editBt.addSelectionListener(listener);
//	    editBt.setEnabled(false);
	    toolBar.add(editBt);
	    return toolBar;
	}
	
	protected Grid<BeanModel> createGrid(){
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		ColumnConfig config = new ColumnConfig(Constants.DATE_TAG, Strings.DATE, 40);
		config.setDateTimeFormat(DateTimeFormat.getFormat("dd.MM.yyyy"));
		columns.add(config);
		
		config = new ColumnConfig(Constants.START_TAG, Strings.START, 40);
		config.setDateTimeFormat(DateTimeFormat.getFormat("hh:mm"));
		columns.add(config);
		
		config = new ColumnConfig(Constants.BREAK_TAG, Strings.BREAK, 40);
		config.setDateTimeFormat(DateTimeFormat.getFormat("hh:mm"));
		columns.add(config);
		
		config = new ColumnConfig(Constants.END_TAG, Strings.END, 40);
		config.setDateTimeFormat(DateTimeFormat.getFormat("hh:mm"));
		columns.add(config);
		
	    columns.add(new ColumnConfig(Constants.COMMENT_TAG, Strings.COMMENT, 300));

	    ColumnModel cm = new ColumnModel(columns);	    
	    
	    
	    
	    RpcProxy<List<Work>> proxy = new RpcProxy<List<Work>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<Work>> callback) {
				service.getWorksOfMonth(WebDesktop.currentWorker, callback);
				
			}
		};
		
		BeanModelReader reader = new BeanModelReader();
		loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
	    
	    ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
	    store.setSortField(Constants.DATE_TAG);
	    loader.load();
	    grid = new Grid<BeanModel>(store, cm);    
	    grid.setBorders(true);	
	    grid.getSelectionModel().setSelectionMode(SelectionMode.MULTI);	    
	    return grid;
	}
	
	public void updateTable(){
		loader.load();
	}
	
	public void addWork(ModelData data){
//		grid.getStore().add(data);		
	}
	
	public void modifyWork(ModelData data){
//		grid.getStore().update(data);
	}

//	public static class Work extends BaseModel{
//		public Work(){
//			
//		}
//		
//		public Work(String date, String start, String _break, String end, String comment){
//			setDay(date);
//			setStart(start);
//			setBreak(_break);			
//			setEnd(end);
//			setComment(comment);
//		}
//		
//		public void setDay(String date){
//			set(Constants.DATE_TAG, date);
//		}
//		
//		public void setStart(String start){
//			set(Constants.START_TAG, start);
//		}
//		
//		public void setBreak(String _break){
//			set(Constants.BREAK_TAG, _break);
//		}
//		
//		public void setEnd(String end){
//			set(Constants.END_TAG, end);
//		}
//		
//		public void setComment(String comment){
//			set(Constants.COMMENT_TAG, comment);
//		}
//		
//		public String getDay(){
//			return (String)get(Constants.DATE_TAG);
//		}
//		
//		public String getStart(){
//			return (String)get(Constants.START_TAG);
//		}
//		
//		public String getBreak(){
//			return (String)get(Constants.BREAK_TAG);
//		}
//		
//		public String getEnd(){
//			return (String)get(Constants.END_TAG);
//		}
//		
//		public String getComment(){
//			return (String)get(Constants.COMMENT_TAG);
//		}
//	}
}
