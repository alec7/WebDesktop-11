package com.hellbelk.client.bean;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.util.DateWrapper;

public class Work implements Serializable{
	private Worker worker;
	
	private Date date;
	private Date start;
	private Date _break;
	private Date end;
	private String comment;
	
	public Work(){}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getBreak() {
		return _break;
	}

	public void setBreak(Date _break) {
		this._break = _break;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object obj) {
		DateWrapper dw1 = new DateWrapper(date);
		DateWrapper dw2 = new DateWrapper(((Work)obj).date);
		return dw1.getDate() == dw2.getDate() && dw1.getMonth() == dw2.getMonth() && dw1.getFullYear() == dw2.getFullYear();
	}
}
