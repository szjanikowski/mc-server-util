package com.szjanikowski.mc.filters.monitoring.mock;

import com.szjanikowski.mc.filters.monitoring.MinecraftServerApi;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import jakarta.inject.Singleton;

@Singleton
@Requires(notEnv = Environment.GOOGLE_COMPUTE)
public class AlwaysZeroPlayers implements MinecraftServerApi {

	@Override
	public int getCurrentNumberOfPlayers() {
		return 0;
	}
}
