package com.hellbelk.client.ui.worktime;


import java.util.Date;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hellbelk.client.Constants;
import com.hellbelk.client.Strings;
import com.hellbelk.client.bean.Work;
import com.hellbelk.client.service.WorkServiceAsync;
import com.hellbelk.client.ui.WebDesktop;

public class EditWork extends Dialog {
	public static final int ADD = 0;
	public static final int EDIT = 1;
	
	private final WorkServiceAsync service = (WorkServiceAsync) Registry.get(WebDesktop.WORK_SERVICE);
	
	protected final int mode;
	
	WorkTimeWindow parent;
	
	public EditWork(WorkTimeWindow parent){
		this(parent, null);
	}
	
	public EditWork(final WorkTimeWindow parent, final BeanModel data){
		this.parent = parent;
		mode = data == null ? ADD : EDIT;
		
		setHeading(mode == ADD ? Strings.ADD_WORK: Strings.EDIT_WORK);

		setModal(true);
		
		FormPanel editWorkForm = new FormPanel();
		editWorkForm.setBodyBorder(false);
		editWorkForm.setHeaderVisible(false);
        
        final TimeField startTf = new TimeField();        
        startTf.setFieldLabel(Strings.START);
        startTf.setName(Constants.START_TAG);
        editWorkForm.add(startTf, new FormData("100%"));
        
        final TimeField breakTf = new TimeField();
        breakTf.setFieldLabel(Strings.BREAK);
        breakTf.setName(Constants.BREAK_TAG);
        editWorkForm.add(breakTf, new FormData("100%"));
        
        final TimeField endTf = new TimeField();
        endTf.setFieldLabel(Strings.END);
        endTf.setName(Constants.END_TAG);
        editWorkForm.add(endTf, new FormData("100%"));
        
        final TextArea commentAr = new TextArea();
        commentAr.setFieldLabel(Strings.COMMENT);
        commentAr.setName(Constants.COMMENT_TAG);
        editWorkForm.add(commentAr, new FormData("100%"));
        
        if(mode == EDIT){        	
        	
        	Work work = data.getBean();
        	
        	startTf.setDateValue(work.getStart());
        	breakTf.setDateValue(work.getBreak());
        	endTf.setDateValue(work.getEnd());
        	
        	if(!Util.isEmptyString(work.getComment())){
	        	commentAr.setValue(work.getComment());
	        }
        }
        Button ok = this.getButtonById(Dialog.OK);        
        FormButtonBinding binding = new FormButtonBinding(editWorkForm);
        binding.addButton(ok);
        
        ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				Work work = mode == ADD ? new Work() : (Work)data.getBean();				
				work.setDate(new Date(System.currentTimeMillis()));
				work.setStart(startTf.getDateValue());
				work.setBreak(breakTf.getDateValue());
				work.setEnd(endTf.getDateValue());
				work.setComment(commentAr.getValue());
				
				
				if(mode == ADD){
					service.addWork(work, new AsyncCallback<Boolean>() {
						
						@Override
						public void onSuccess(Boolean result) {
							parent.updateTable();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							MessageBox.alert(Strings.ERROR, Strings.ADD_WORK_ERROR, null);							
						}
					});
				}else{
					service.updateWork(work, new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							MessageBox.alert(Strings.ERROR, Strings.UPDATE_WORK_ERROR, null);
						}

						@Override
						public void onSuccess(Boolean result) {
							parent.updateTable();
						}
					});
				}
				EditWork.this.hide();
			}
		});
        
        add(editWorkForm);
	}
	
	
}
