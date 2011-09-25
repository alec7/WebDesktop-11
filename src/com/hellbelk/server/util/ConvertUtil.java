package com.hellbelk.server.util;

import com.hellbelk.client.bean.Work;
import com.hellbelk.client.bean.Worker;
/**
 * Convert server POJOs to client beans and back
 * @author hellbelk
 *
 */
public class ConvertUtil {

	/**
	 * Convert server POJO to client bean
	 * @param work server POJO
	 * @return client bean
	 */
	public static Work convert(com.hellbelk.server.pojo.Work work){
		Work w = new Work();
		w.setWorker(convert(work.getWorker()));
		w.setDate(work.getDate());
		w.setStart(work.getStart());
		w.setBreak(work.getBreak());
		w.setEnd(work.getEnd());
		w.setComment(work.getComment());
		return w;
	}
	
	/**
	 * Convert client bean to server POJO
	 * @param work client bean
	 * @return server POJO
	 */
	public static com.hellbelk.server.pojo.Work convert(Work work){
		com.hellbelk.server.pojo.Work w = new com.hellbelk.server.pojo.Work();
		w.setWorker(convert(work.getWorker()));
		w.setDate(work.getDate());
		w.setStart(work.getStart());
		w.setBreak(work.getBreak());
		w.setEnd(work.getEnd());
		w.setComment(work.getComment());
		return w;
	}
	
	/**
	 * Convert server POJO to client bean
	 * @param worker server POJO
	 * @return client bean
	 */
	public static Worker convert(com.hellbelk.server.pojo.Worker worker){
		Worker w = new Worker();
		w.setId(worker.getId());
		w.setFirstName(worker.getFirstName());
		w.setLastName(worker.getLastName());
		return w;
	}
	
	/**
	 * Convert client bean to server POJO
	 * @param worker client bean
	 * @return server POJO
	 */
	public static com.hellbelk.server.pojo.Worker convert(Worker worker){
		com.hellbelk.server.pojo.Worker w = new com.hellbelk.server.pojo.Worker();
		w.setId(worker.getId());
		w.setFirstName(worker.getFirstName());
		w.setLastName(worker.getLastName());
		return w;
	}
}
