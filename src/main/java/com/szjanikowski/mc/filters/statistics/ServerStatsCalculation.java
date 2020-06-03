package com.szjanikowski.mc.filters.statistics;

import com.szjanikowski.mc.pipes.ServerState;
import com.szjanikowski.mc.pipes.ServerStatistics;
import io.reactivex.Observable;

public class ServerStatsCalculation {

	public Observable<ServerStatistics> transform(Observable<ServerState> serverStateObs) {
		return serverStateObs
				.map(ServerState::getNumberOfPlayers)
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
				})
				.map(ServerStatistics::new);
	}
}
