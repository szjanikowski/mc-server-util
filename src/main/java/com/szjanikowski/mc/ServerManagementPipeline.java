package com.szjanikowski.mc;

import com.szjanikowski.mc.filters.monitoring.ServerMonitoring;
import com.szjanikowski.mc.filters.statistics.ServerStatsCalculation;
import com.szjanikowski.mc.filters.reactions.ReactionsTriggers;
import io.micronaut.context.annotation.Context;
import lombok.val;

import javax.annotation.PostConstruct;

@Context
public class ServerManagementPipeline {

	private final ServerMonitoring serverMonitoring;
	private final ServerStatsCalculation serverStatsCalculation = new ServerStatsCalculation();
	private final ReactionsTriggers reactionsTriggers;

	public ServerManagementPipeline(ServerMonitoring serverMonitoring, ReactionsTriggers reactionsTriggers) {
		this.serverMonitoring = serverMonitoring;
		this.reactionsTriggers = reactionsTriggers;
	}

	@PostConstruct
	void initializePipeline() {
		//Source
		val serverStateObs = serverMonitoring.getServerStateObs();
		//Transformation
		val serverStatsObs = serverStatsCalculation.transform(serverStateObs);
		//Sink
		reactionsTriggers.subscribe(serverStatsObs);
	}
}
