package com.hellbelk.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hellbelk.server.pojo.Work;
import com.hellbelk.server.pojo.Worker;

public class WorkDAO {
	public static final String WORKER_ID = "W_ID";
	public static final String DATE = "DATE";
	public static final String START = "DATE";
	public static final String BREAK = "BREAK";
	public static final String END = "END";
	public static final String COMMENT = "COMMENT";
	
	protected NamedParameterJdbcTemplate jdbcTemplate = null;
	
	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public int insert(Work work) {
		return 0;
	}
	
	public int delete(Work work) {
		return 0;
	}
	
	public int update(Work work) {
		return 0;
	}
	
	public Collection<Work> getToPeriod(Worker worker, Date start, Date end){
		List<Work> work = new LinkedList<Work>();
		Map<String, String> parameters = new HashMap<String, String>();
//		parameters.put(key, value)
		List<Map<String, Object>> lWorkers = jdbcTemplate.queryForList("select * from work where w_id = :worker_id", new HashMap<String, Object>());
		
		return new ArrayList<Work>();
	}
	
	private class WorkMapper implements RowMapper<Work>{

		@Override
		public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
