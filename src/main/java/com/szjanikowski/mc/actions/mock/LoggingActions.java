package com.szjanikowski.mc.actions.mock;

import com.szjanikowski.mc.ZeroPeriodExceededAction;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import javax.inject.Singleton;

@Singleton
@Requires(notEnv = Environment.GOOGLE_COMPUTE)
public class LoggingActions implements ZeroPeriodExceededAction {
	@Override
	public void zeroPlayersPeriodOf(int minutes) {
		System.out.println("Action of zero players period of " + minutes + " minutes");
	}
}
