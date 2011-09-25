package com.hellbelk.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hellbelk.client.bean.Worker;

public interface WorkerServiceAsync {

	void getAllWorkers(AsyncCallback<List<Worker>> callback);

}
