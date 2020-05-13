package com.szjanikowski.mc.monitoring.rcon;

import com.szjanikowski.mc.MinecraftServerApi;

import javax.inject.Singleton;

@Singleton
public class MinecraftRconConnector implements MinecraftServerApi {

	@Override
	public int getCurrentNumberOfPlayers() {
		return 0;
	}
}
