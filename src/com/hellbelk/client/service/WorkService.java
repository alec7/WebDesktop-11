package com.hellbelk.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hellbelk.client.bean.Work;
import com.hellbelk.client.bean.Worker;

@RemoteServiceRelativePath("worker_service")
public interface WorkService extends RemoteService{

	List<Work> getWorksOfMonth(Worker worker);	
	boolean addWork(Work work);
	boolean removeWork(Work work);
	boolean updateWork(Work work);
}
