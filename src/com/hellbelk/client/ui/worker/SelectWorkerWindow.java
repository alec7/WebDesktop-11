package com.hellbelk.client.ui.worker;

import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hellbelk.client.Strings;
import com.hellbelk.client.bean.Worker;
import com.hellbelk.client.service.WorkerServiceAsync;
import com.hellbelk.client.ui.WebDesktop;

public class SelectWorkerWindow extends Dialog {
	private final WorkerServiceAsync service = (WorkerServiceAsync) Registry.get(WebDesktop.WORKER_SERVICE);
	public SelectWorkerWindow(){
		setModal(true);
		setClosable(false);
		setResizable(false);
		setAutoWidth(true);
		setAutoHide(true);
		
		setHeading(Strings.SELECT_WORKER);
		
		VBoxLayout layout = new VBoxLayout();  
        layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);  
        setLayout(layout);
		
		final ComboBox<BeanModel> workerCb = createWorkerComboBox();
		
		Button ok = getButtonById(Dialog.OK);
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(workerCb.getSelection().size() == 0)
					WebDesktop.currentWorker = workerCb.getSelection().get(0).getBean();					
				else
					MessageBox.info(Strings.WARNING, Strings.NO_WORKER_SELECTED, null);				
			}});
				
		add(workerCb);
	}
	
	private ComboBox<BeanModel> createWorkerComboBox(){
		RpcProxy<List<Worker>> proxy = new RpcProxy<List<Worker>>() {

			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<Worker>> callback) {
				service.getAllWorkers(callback);
				
			}
		};
		
		BeanModelReader reader = new BeanModelReader();
		BaseListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
	    
	    ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
	    loader.load();
	    
	    ComboBox<BeanModel> workersCb = new ComboBox<BeanModel>();
	    workersCb.setStore(store);
	    workersCb.setEmptyText(Strings.EMPTY_WORKER_COMBO_BOX);
	    return workersCb;
	}
}
