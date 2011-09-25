package com.hellbelk.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hellbelk.client.bean.Work;
import com.hellbelk.client.bean.Worker;

public interface WorkServiceAsync {

	void getWorksOfMonth(Worker worker, AsyncCallback<List<Work>> callback);

	void addWork(Work work, AsyncCallback<Boolean> callback);

	void removeWork(Work work, AsyncCallback<Boolean> callback);

	void updateWork(Work work, AsyncCallback<Boolean> callback);

}
