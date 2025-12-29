package com.szjanikowski.mc.filters.monitoring;

import com.szjanikowski.mc.pipes.ServerState;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class ServerMonitoring {

	private static final Logger LOG = LoggerFactory.getLogger(ServerMonitoring.class);

	private final BehaviorSubject<ServerState> numberOfPlayersOnServer = BehaviorSubject.create();

	private final MinecraftServerApi minecraftServerApi;

	public ServerMonitoring(MinecraftServerApi minecraftServerApi) {
		this.minecraftServerApi = minecraftServerApi;
	}

	//SOURCE
	@Scheduled(fixedRate = "${mcutil.checking.frequency:30s}")
	public void checkForNumberOfPlayers() {
		int numberOfPlayers = minecraftServerApi.getCurrentNumberOfPlayers();
		LOG.trace("Number of players: " + numberOfPlayers);
		numberOfPlayersOnServer.onNext(new ServerState(numberOfPlayers));
	}

	public Observable<ServerState> getServerStateObs() {
		return numberOfPlayersOnServer;
	}
}
