package com.stg.bluckau.qa;

import java.io.Serializable;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.testng.Reporter;

// note: class name need not match the @Plugin name.
@Plugin(name = "REPORTER", category = "Core", elementType = "appender", printObject = true)
public final class ReporterAppender extends AbstractAppender
{

	protected ReporterAppender(String name, Filter filter,
			Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}

	// The append method is where the appender does the work.
	// Given a log event, you are free to do with it what you want.
	// This example demonstrates:
	// 1. Concurrency: this method may be called by multiple threads concurrently
	// 2. How to use layouts
	// 3. Error handling
	@Override
	public void append(LogEvent event) {
		try {
			Reporter.log(event.toString());
		} catch (Exception ex) {
			if (!ignoreExceptions()) {
				throw new AppenderLoggingException(ex);
			}
		}
	}

	// Your custom appender needs to declare a factory method
	// annotated with `@PluginFactory`. Log4j will parse the configuration
	// and call this factory method to construct an appender instance with
	// the configured attributes.
	@PluginFactory
	public static ReporterAppender createAppender(
			@PluginAttribute("name") String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout,
			@PluginElement("Filter") final Filter filter,
			@PluginAttribute("otherAttribute") String otherAttribute) {
		if (name == null) {
			LOGGER.error("No name provided for ReporterAppender");
			return null;
		}
		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}
		return new ReporterAppender(name, filter, layout, true);
	}
}