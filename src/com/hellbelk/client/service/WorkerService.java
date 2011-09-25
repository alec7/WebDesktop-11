package com.hellbelk.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hellbelk.client.bean.Worker;

@RemoteServiceRelativePath("work_service")
public interface WorkerService extends RemoteService{
	List<Worker> getAllWorkers();
}
