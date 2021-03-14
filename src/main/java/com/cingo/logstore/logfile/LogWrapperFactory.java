package com.cingo.logstore.logfile;

import java.io.File;

public class LogWrapperFactory {
	
	private static final String LOG_FILE = "cingohc.log";

	public LogWrapper build(File file) throws LogWrapperBuildException {
		try {
			return new LogFileWrapper(file);
		} catch (Exception e) {
			throw new LogWrapperBuildException(e);
		}
	}
}
