package com.szjanikowski.mc;

import io.micronaut.context.annotation.Context;


@Context
public class ZeroPlayersTrigger {

	//TODO config of period

	public ZeroPlayersTrigger(ZeroPlayersPeriod zeroPlayersPeriod, ZeroPeriodExceededAction actions) {
		zeroPlayersPeriod.getZeroPlayersPeriod()
				.filter(time -> time > 10000)
				.firstOrError()
				.subscribe(time -> actions.zeroPlayersPeriodExceededBy(5));
	}
}
