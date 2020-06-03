package com.szjanikowski.mc.filters.reactions;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
@Requires(notEnv = Environment.GOOGLE_COMPUTE)
class LoggingActions implements ZeroPeriodExceededAction {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingActions.class);

	@Override
	public void zeroPlayersPeriodOf(int minutes) {
		LOG.info("Taking action after zero players period of " + minutes + " minutes");
	}
}
