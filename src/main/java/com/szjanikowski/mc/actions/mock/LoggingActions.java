package com.szjanikowski.mc.actions.mock;

import com.szjanikowski.mc.ZeroPeriodExceededAction;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import javax.inject.Singleton;

@Singleton
@Requires(notEnv = Environment.GOOGLE_COMPUTE)
public class LoggingActions implements ZeroPeriodExceededAction {
	@Override
	public void zeroPlayersPeriodExceededBy(int seconds) {
		System.out.println("Zero period exceeded by " + seconds + " seconds");
	}
}
