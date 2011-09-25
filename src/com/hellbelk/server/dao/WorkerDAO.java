package com.hellbelk.server.dao;

import java.util.Collection;

import javax.sql.DataSource;

import com.hellbelk.server.pojo.Worker;

public interface WorkerDAO {
	public void setDataSource(final DataSource dataSource);
	public int insert(Worker worker);
	public int delete(Worker worker);
	public int update(Worker worker);
	public Worker find(long id);
	public Collection<Worker> list();
}
