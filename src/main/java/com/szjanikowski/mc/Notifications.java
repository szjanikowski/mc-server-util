package com.szjanikowski.mc;

import io.micronaut.context.annotation.Context;

@Context
public class Notifications {

	public Notifications(ServerMonitoring serverMonitoring, PubSubMsgSending pubSubMsgSending) {
		serverMonitoring.getZeroPlayersPeriod()
				.filter(time -> time > 5000)
				.firstOrError()
				.subscribe(time -> pubSubMsgSending.publishNoPlayersForSeconds(5));

	}
}
