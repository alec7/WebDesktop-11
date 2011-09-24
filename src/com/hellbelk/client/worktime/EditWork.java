package com.hellbelk.client.worktime;

import java.util.Date;

import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.Dialog;
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
	
	protected int mode;
	
	public EditWork(){
		this(null, null, null, null);
	}
	
	public EditWork(Date start, Date lunch, Date end, String comment){
		mode = (start == null && lunch == null && end == null) ? ADD : EDIT;
		
		setHeading(mode == ADD ? Strings.ADD_WORK: Strings.EDIT_WORK);

		setModal(true);
		
		FormPanel addWorkForm = new FormPanel();
		addWorkForm.setBodyBorder(false);
		addWorkForm.setHeaderVisible(false);
        FormData formData = new FormData("100%");
        
        final TimeField startTf = new TimeField();        
        startTf.setFieldLabel(Strings.START);
        startTf.setName(Constants.START_TAG);
        addWorkForm.add(startTf, new FormData("100%"));
        
        final TimeField breakTf = new TimeField();
        breakTf.setFieldLabel(Strings.BREAK);
        breakTf.setName(Constants.BREAK_TAG);
        addWorkForm.add(breakTf, new FormData("100%"));
        
        final TimeField endTf = new TimeField();
        endTf.setFieldLabel(Strings.END);
        endTf.setName(Constants.END_TAG);
        addWorkForm.add(endTf, new FormData("100%"));
        
        final TextArea commentAr = new TextArea();
        commentAr.setFieldLabel(Strings.COMMENT);
        commentAr.setName(Constants.COMMENT_TAG);
        addWorkForm.add(commentAr, new FormData("100%"));
        
        if(mode == EDIT){
	        start.setTime(start.getTime());
	        lunch.setTime(lunch.getTime());
	        end.setTime(end.getTime());
	        
	        if(!Util.isEmptyString(comment)){
	        	commentAr.setValue(comment);
	        }
        }
        
        
        
        
        add(addWorkForm);
	}
}
