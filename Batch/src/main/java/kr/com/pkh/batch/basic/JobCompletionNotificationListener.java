package kr.com.pkh.batch.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

// 배치작업이 완료되는경우 알림 (spring batch 5.0)
//@Component
//public class JobCompletionNotificationListener implements JobExecutionListener {
//
//	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
//
//	private final JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
//
//	@Override
//	public void afterJob(JobExecution jobExecution) {
//		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
//			log.info("!!! JOB FINISHED! Time to verify the results");
//
//			jdbcTemplate.query("SELECT first_name, last_name FROM people",
//				(rs, row) -> new Person(
//					rs.getString(1),
//					rs.getString(2))
//			).forEach(person -> log.info("Found <{{}}> in the database.", person));
//		}
//	}
//}
