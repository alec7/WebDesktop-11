package com.hellbelk.server.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hellbelk.client.bean.Work;
import com.hellbelk.client.bean.Worker;
import com.hellbelk.client.service.WorkService;
import com.hellbelk.server.dao.WorkDAOImpl;
import com.hellbelk.server.util.ConvertUtil;


@SuppressWarnings("serial")
@Controller
public class WorkServiceImpl extends RemoteServiceServlet implements
		WorkService {
	
	@Autowired
	private WorkDAOImpl workDAO = null;
	
	@Override
	public List<Work> getWorksOfMonth(Worker worker) {
		
		List<Work> works = new ArrayList<Work>();
		
		//Save values of fields and reset calendar to avoid situation when we have time "1.05.2011 23:59"
		Calendar c = Calendar.getInstance();
		int firstDay = c.getMinimum(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		c.clear(); 
		
		c.set(Calendar.DATE, firstDay);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		
		Date start = c.getTime();
		c.add(Calendar.MONTH, 1);
		Date end = c.getTime();
		
		Collection<com.hellbelk.server.pojo.Work> lWorks = workDAO.getToPeriod(ConvertUtil.convert(worker), start, end);
		for(com.hellbelk.server.pojo.Work work : lWorks){
			works.add(ConvertUtil.convert(work));
		}
		
		return works;
	}

	@Override
	public boolean addWork(Work work) {
		return workDAO.insert(ConvertUtil.convert(work)) > 0;
	}

	@Override
	public boolean removeWork(Work work) {
		return workDAO.delete(ConvertUtil.convert(work)) > 0;
	}

	@Override
	public boolean updateWork(Work work) {
		return workDAO.update(ConvertUtil.convert(work)) > 0;
	}	
	
}
