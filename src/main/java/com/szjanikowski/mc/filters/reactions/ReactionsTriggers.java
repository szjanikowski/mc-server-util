package com.szjanikowski.mc.filters.reactions;

import com.szjanikowski.mc.pipes.ServerStatistics;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Property;
import io.reactivex.Observable;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//SINK
@Context
public class ReactionsTriggers {

	private static final Logger LOG = LoggerFactory.getLogger(ReactionsTriggers.class);

	private static final int MINUTE = 1000 * 60;
	private static final int DIVIDER = MINUTE * 10;
	private final int triggerInMinutes;
	private final ZeroPeriodExceededAction action;

	public ReactionsTriggers(@Property(name = "mcutil.trigger.minutes") int triggerMinutes,
							 ZeroPeriodExceededAction action) {
		this.triggerInMinutes = triggerMinutes;
		this.action = action;
	}

	public void subscribe(Observable<ServerStatistics> zeroPlayersPeriodObs) {
		LOG.info("Will trigger action after " + triggerInMinutes + " minutes of zero players.");
		val zeroPlayersPeriodInMillisObs = zeroPlayersPeriodObs.map(ServerStatistics::getZeroPlayersPeriodInMillis);
		zeroPlayersPeriodInMillisObs
				.scan( (previousOne, newOne) -> {
					if (previousOne / DIVIDER != newOne / DIVIDER) {
						LOG.info("Zero players for more than " + newOne / (MINUTE) + " minutes.");
					}
					return newOne;
				}).subscribe();
		zeroPlayersPeriodInMillisObs
				.filter(time -> time > triggerInMinutes * MINUTE)
				.firstOrError()
				.subscribe(time -> action.zeroPlayersPeriodOf(triggerInMinutes));
	}
}
