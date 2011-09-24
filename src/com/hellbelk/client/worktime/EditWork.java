package com.hellbelk.client.worktime;

import java.text.DateFormat;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.hellbelk.client.Strings;
import com.hellbelk.shared.Constants;

public class EditWork extends Dialog {
	public static final int ADD = 0;
	public static final int EDIT = 1;
	
	protected final int mode;
	
	WorkTimeWindow parent;
	
	public EditWork(WorkTimeWindow parent){
		this(parent, null);
	}
	
	public EditWork(WorkTimeWindow parent, ModelData data){
		this.parent = parent;
		mode = data == null ? ADD : EDIT;
		
		setHeading(mode == ADD ? Strings.ADD_WORK: Strings.EDIT_WORK);

		setModal(true);
		
		FormPanel editWorkForm = new FormPanel();
		editWorkForm.setBodyBorder(false);
		editWorkForm.setHeaderVisible(false);
        FormData formData = new FormData("100%");
        
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
        	startTf.setValueField((String) data.get(Constants.START_TAG));
        	breakTf.setValueField((String) data.get(Constants.BREAK_TAG));
        	endTf.setValueField((String) data.get(Constants.END_TAG));
//        	startTf.setDateValue();
//	        start.setTime(start.getTime());
//	        lunch.setTime(lunch.getTime());
//	        end.setTime(end.getTime());
//	        
	        if(!Util.isEmptyString((String) data.get(Constants.COMMENT_TAG))){
	        	commentAr.setValue((String) data.get(Constants.COMMENT_TAG));
	        }
        }
        Button ok = this.getButtonById(Dialog.OK);        
        FormButtonBinding binding = new FormButtonBinding(editWorkForm);
        binding.addButton(ok);
        
        ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(mode == ADD){
					ModelData data = new BaseModelData();
					data.set(Constants.DATE_TAG, new DateWrapper(System.currentTimeMillis()).toString());
					data.set(Constants.START_TAG, new DateWrapper(startTf.getDateValue()).toString());
					data.set(Constants.BREAK_TAG, new DateWrapper(breakTf.getDateValue()).toString());
					data.set(Constants.END_TAG, new DateWrapper(endTf.getDateValue()).toString());
					data.set(Constants.COMMENT_TAG, commentAr.getValue());
				}else{
					
				}
			}
		});
        
        add(editWorkForm);
	}
	
	
}
