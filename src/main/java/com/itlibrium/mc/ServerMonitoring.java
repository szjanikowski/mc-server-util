package com.itlibrium.mc;

import io.micronaut.scheduling.annotation.Scheduled;
import io.netty.util.internal.ThreadLocalRandom;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import javax.inject.Singleton;

@Singleton
public class ServerMonitoring {

	private final BehaviorSubject<Integer> numberOfPlayersOnServer = BehaviorSubject.create();


	public Observable<Integer> getNumberOfPlayersOnServer() {
		return numberOfPlayersOnServer;
	}

	@Scheduled(fixedRate = "1s")
	public void checkForNumberOfPlayers() {
		numberOfPlayersOnServer.onNext(ThreadLocalRandom.current().nextInt(0, 6));
	}

}
