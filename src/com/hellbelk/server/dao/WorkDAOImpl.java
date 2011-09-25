package com.hellbelk.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.hellbelk.server.pojo.Work;
import com.hellbelk.server.pojo.Worker;

public class WorkDAOImpl implements WorkDAO{
	public static final String WORKER_ID = "W_ID";
	public static final String DATE = "DATE";
	public static final String START = "START";
	public static final String BREAK = "BREAK";
	public static final String END = "END";
	public static final String COMMENT = "COMMENT";
	
	protected NamedParameterJdbcTemplate jdbcTemplate = null;
	
	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insert(Work work) {
		String query = "insert into work(`w_id`, 'date', `start`, `break`, `end`, `comment`) values(:W_ID, :DATE, :START, :BREAK, :END, :COMMENT)";
		Map<String, Object> parameners = new HashMap<String, Object>();
		parameners.put(WORKER_ID, work.getWorker().getId());
		parameners.put(DATE, work.getDate());
		parameners.put(START, work.getStart());
		parameners.put(BREAK, work.getBreak());
		parameners.put(END, work.getEnd());
		parameners.put(COMMENT, work.getComment());
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public int delete(Work work) {
		String query = "delete work where `date` = :DATE";
		Map<String, Object> parameners = new HashMap<String, Object>();
		parameners.put(DATE, work.getDate());
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public int update(Work work) {
		String query = "update work set `start` = :START, `break` = :BREAK, `end` = :END, `comment` = :COMMENT where `date` = :DATE";
		Map<String, Object> parameners = new HashMap<String, Object>();
		parameners.put(DATE, work.getDate());
		parameners.put(START, work.getStart());
		parameners.put(BREAK, work.getBreak());
		parameners.put(END, work.getEnd());
		parameners.put(COMMENT, work.getComment());
		return jdbcTemplate.update(query, parameners);
	}
	
	@Override
	public Collection<Work> getToPeriod(Worker worker, Date start, Date end){
		String query = "select * from work where `w_id` = :id and `date` > :start and `date` < :end";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", worker.getId());
		parameters.put("start", start);
		parameters.put("end", end);
		return jdbcTemplate.query(query, parameters, new WorkMapper(worker));
	}
	
	private class WorkMapper implements RowMapper<Work>{
		Worker worker;
		
		public WorkMapper(Worker worker){
			this.worker = worker; 
		}
		
		@Override
		public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
			Work work = new Work();
			work.setWorker(worker);
			work.setDate(rs.getDate(DATE));
			work.setStart(rs.getTimestamp(START));
			work.setBreak(rs.getTimestamp(BREAK));
			work.setEnd(rs.getTimestamp(END));
			work.setComment(rs.getString(COMMENT));
			return work;
		}
		
	}
}
