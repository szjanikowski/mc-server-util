package com.szjanikowski.mc;

import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import javax.inject.Singleton;

@Singleton
public class ServerMonitoring {

	private final BehaviorSubject<Integer> numberOfPlayersOnServer = BehaviorSubject.create();

	private final MinecraftServerApi minecraftServerApi;

	public ServerMonitoring(MinecraftServerApi minecraftServerApi) {
		this.minecraftServerApi = minecraftServerApi;
	}

	@Scheduled(fixedRate = "1s")
	public void checkForNumberOfPlayers() {
		int numberOfPlayers = minecraftServerApi.getCurrentNumberOfPlayers();
		numberOfPlayersOnServer.onNext(numberOfPlayers);

	}

	public Observable<Integer> getNumberOfPlayersOnServer() {
		return numberOfPlayersOnServer;
	}
}
