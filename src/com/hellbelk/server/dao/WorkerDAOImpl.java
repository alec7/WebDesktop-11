package com.hellbelk.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hellbelk.server.pojo.Worker;

public class WorkerDAOImpl implements WorkerDAO{
	private final static String ID = "ID";
	private final static String FIRST_NAME = "F_NAME";
	private final static String LAST_NAME = "L_NAME";
	
	protected NamedParameterJdbcTemplate jdbcTemplate = null;
	
	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insert(Worker worker){
		String query = "insert into worker(f_name, l_name) values(:F_NAME, :L_NAME)";
		Map<String, Object> parameners = new HashMap<String, Object>();
		parameners.put(ID, worker.getId());
		parameners.put(FIRST_NAME, worker.getFirstName());
		parameners.put(LAST_NAME, worker.getLastName());
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public int delete(Worker worker){
		String query = "update worker where id = :ID";
		Map<String, Long> parameners = new HashMap<String, Long>();
		parameners.put(ID, worker.getId());		
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public int update(Worker worker){
		String query = "update worker set f_name = :F_NAME, l_name = :L_NAME where id = :ID";
		Map<String, Object> parameners = new HashMap<String, Object>();
		parameners.put(ID, worker.getId());
		parameners.put(FIRST_NAME, worker.getFirstName());
		parameners.put(LAST_NAME, worker.getLastName());
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public Worker find(long id){
		String query = "select * from worker where id = :ID";
		Map<String, Long> parameners = new HashMap<String, Long>();
		parameners.put(ID, id);
		return jdbcTemplate.queryForObject(query, parameners, new WorkerMapper());
	}
	
	@Override
	public Collection<Worker> list(){
		String query = "select * from worker";
		return jdbcTemplate.query(query, new HashMap<String, Object>(), new WorkerMapper());
	}
	
	private class WorkerMapper implements RowMapper<Worker>{

		@Override
		public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
			Worker worker = new Worker();
			worker.setId(rs.getLong(ID));
			worker.setFirstName(rs.getString(FIRST_NAME));
			worker.setLastName(rs.getString(LAST_NAME));
			return worker;
		}
		
	}
}
