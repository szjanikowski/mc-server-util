package com.szjanikowski.mc;

import com.szjanikowski.mc.actions.gcpsdk.GoogleCloudSdkActions;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class ServerMonitoring {

	private static final Logger LOG = LoggerFactory.getLogger(ServerMonitoring.class);

	private final BehaviorSubject<Integer> numberOfPlayersOnServer = BehaviorSubject.create();

	private final MinecraftServerApi minecraftServerApi;

	public ServerMonitoring(MinecraftServerApi minecraftServerApi) {
		this.minecraftServerApi = minecraftServerApi;
	}

	@Scheduled(fixedRate = "${mcutil.checking.frequency:20s}")
	public void checkForNumberOfPlayers() {
		int numberOfPlayers = minecraftServerApi.getCurrentNumberOfPlayers();
		LOG.info("Number of players: " + numberOfPlayers);
		numberOfPlayersOnServer.onNext(numberOfPlayers);
	}

	public Observable<Integer> getNumberOfPlayersOnServer() {
		return numberOfPlayersOnServer;
	}
}
