package com.szjanikowski.mc;

import io.micronaut.context.annotation.Context;
import io.reactivex.Observable;

@Context
public class ZeroPlayersPeriod {

	private final Observable<Long> zeroPlayersPeriod;

	public ZeroPlayersPeriod(ServerMonitoring serverMonitoring) {
		zeroPlayersPeriod = serverMonitoring.getNumberOfPlayersOnServer()
				.timestamp()
				.scan((startOfZeroPlayersPeriod, numberOfPlayersWithTime) ->
						numberOfPlayersWithTime.value() > 0 ?
								numberOfPlayersWithTime : startOfZeroPlayersPeriod
				)
				.timestamp()
				.map(startOfZeroPlayersWithCurrentTime -> {
					long currentTime = startOfZeroPlayersWithCurrentTime.time();
					long timeOfZeroPlayersPeriodStart = startOfZeroPlayersWithCurrentTime.value().time();
					return currentTime - timeOfZeroPlayersPeriodStart;
				});

	}

	public Observable<Long> getZeroPlayersPeriod() {
		return zeroPlayersPeriod;
	}
}
