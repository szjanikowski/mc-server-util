package com.szjanikowski.mc;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Context
public class ZeroPlayersTrigger {

	private static final Logger LOG = LoggerFactory.getLogger(ZeroPlayersTrigger.class);

	private static final int MINUTE = 1000 * 60;
	private static final int DIVIDER = MINUTE * 10;

	public ZeroPlayersTrigger(@Property(name = "mcutil.trigger.minutes") int triggerMinutes,
							  ZeroPlayersPeriod zeroPlayersPeriod,
							  ZeroPeriodExceededAction actions) {
		LOG.info("Will trigger action after " + triggerMinutes + " minutes of zero players.");
		zeroPlayersPeriod.getZeroPlayersPeriod().scan( (previousOne, newOne) -> {
			if (previousOne / DIVIDER != newOne / DIVIDER) {
				LOG.info("Zero players for more than " + newOne / (MINUTE) + " minutes.");
			}
			return newOne;
		}).subscribe();
		zeroPlayersPeriod.getZeroPlayersPeriod()
				.filter(time -> time > triggerMinutes * MINUTE)
				.firstOrError()
				.subscribe(time -> actions.zeroPlayersPeriodOf(triggerMinutes));
	}
}
