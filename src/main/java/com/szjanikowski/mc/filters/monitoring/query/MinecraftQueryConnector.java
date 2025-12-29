package com.szjanikowski.mc.filters.monitoring.query;

import com.szjanikowski.mc.filters.monitoring.MinecraftServerApi;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import jakarta.inject.Singleton;

@Singleton
@Requires(env = Environment.GOOGLE_COMPUTE)
public class MinecraftQueryConnector implements MinecraftServerApi {

	@Override
	public int getCurrentNumberOfPlayers() {
		MCQuery mcQuery = new MCQuery("localhost", 25565);
		QueryResponse response = mcQuery.basicStat();
		return response.getOnlinePlayers();
	}
}
