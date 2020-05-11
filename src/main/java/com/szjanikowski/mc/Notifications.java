package com.szjanikowski.mc;

import io.micronaut.context.annotation.Context;

import javax.annotation.PostConstruct;

@Context
public class Notifications {

	private final ServerMonitoring serverMonitoring;
	private final PubSubMsgSending pubSubMsgSending;

	public Notifications(ServerMonitoring serverMonitoring, PubSubMsgSending pubSubMsgSending) {
		this.serverMonitoring = serverMonitoring;
		this.pubSubMsgSending = pubSubMsgSending;
	}

	@PostConstruct
	public void init() {
		serverMonitoring.getNumberOfPlayersOnServer()
				.filter(numberOfPlayers -> numberOfPlayers == 0)
				.window(5) //TODO config !!
				.subscribe(window -> pubSubMsgSending.publishNoPlayersForSeconds(5));

	}


}
