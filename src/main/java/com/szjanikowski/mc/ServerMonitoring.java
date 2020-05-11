package com.szjanikowski.mc;

import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import javax.inject.Singleton;

@Singleton
public class ServerMonitoring {

	private final BehaviorSubject<Integer> numberOfPlayersOnServer = BehaviorSubject.create();

	private final Observable<Long> zeroPlayersPeriod;

	@Scheduled(fixedRate = "1s")
	public void checkForNumberOfPlayers() {
//		numberOfPlayersOnServer.onNext(ThreadLocalRandom.current().nextInt(0, 6));
		numberOfPlayersOnServer.onNext(0);
	}

	public ServerMonitoring() {
		zeroPlayersPeriod = numberOfPlayersOnServer
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
