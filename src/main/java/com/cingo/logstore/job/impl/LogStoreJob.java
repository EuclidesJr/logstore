package com.cingo.logstore.job.impl;

import com.cingo.logstore.entity.factory.LogFactory;
import com.cingo.logstore.job.Job;
import com.cingo.logstore.job.JobException;
import com.cingo.logstore.logfile.LogWrapperBuildException;
import com.cingo.logstore.logfile.LogWrapperFactory;
import com.cingo.logstore.repostory.LogRepository;

import java.io.File;
import java.util.Arrays;

public class LogStoreJob implements Job {

	private static final int DEFAULT_INITIAL_OCCURRENCES = 1;
	private LogWrapperFactory logWrapperFactory;
	private LogFactory logFactory;
	private LogRepository logRepository;
	
	public LogStoreJob(LogWrapperFactory logWrapperFactory, LogRepository logRepository, LogFactory logFactory) {
		this.logWrapperFactory = logWrapperFactory;
		this.logRepository = logRepository;
		this.logFactory = logFactory;
	}

	@Override
	public void run() throws JobException {
		try {
			String path = System.getProperty("user.home") + File.separator + "cingo_logs";
			File dir = new File(path);

			File[] files = dir.listFiles();
			for (File child : files) {
				this.logWrapperFactory.build(child).getLineContents().forEach(logLineContent -> {
					this.logRepository.add(this.logFactory.build(logLineContent, DEFAULT_INITIAL_OCCURRENCES));
				});
			}
		} catch (LogWrapperBuildException e) {
			throw new JobException(this.getClass().getName(), e);
		}
		
	}

}
