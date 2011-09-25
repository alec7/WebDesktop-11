package com.hellbelk.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hellbelk.client.bean.Worker;
import com.hellbelk.client.service.WorkerService;
import com.hellbelk.server.dao.WorkerDAO;

@Controller
public class WorkerServiceImpl extends RemoteServiceServlet implements
		WorkerService {

	@Autowired
	private WorkerDAO workerDAO = null;
	
	@Override
	public List<Worker> getAllWorkers() {
		List<Worker> workers = new ArrayList();
		Collection<com.hellbelk.server.pojo.Worker> lWorkers = workerDAO.list();
		for(com.hellbelk.server.pojo.Worker worker : lWorkers){
			Worker w = new Worker();
			w.setFirstName(worker.getFirstName());
			w.setLastName(worker.getLastName());
			w.setId(worker.getId());
			workers.add(w);
		}
		return workers;
	}

}
