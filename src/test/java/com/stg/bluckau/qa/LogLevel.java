package com.stg.bluckau.qa;

public enum LogLevel {
	// Do we want any mapping between the TESTNG verbosity (0 through 10)
	// and the log4j style (7 possibilities)?
	OFF(0), FATAL(1), ERROR(2), WARN(3), INFO(4), DEBUG(8), TRACE(9), ALL(10);
	private int value;

	private LogLevel(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}