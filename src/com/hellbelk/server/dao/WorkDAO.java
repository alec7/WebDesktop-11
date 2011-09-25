package com.hellbelk.server.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hellbelk.server.pojo.Work;
import com.hellbelk.server.pojo.Worker;

public interface WorkDAO {
	public int insert(Work work);
	public int delete(Work work);
	public int update(Work work);
	public Collection<Work> getToPeriod(Worker worker, Date start, Date end);
}
