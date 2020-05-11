package com.itlibrium.mc;

import javax.inject.Singleton;

@Singleton
public class PubSubMsgSending {

	void publishNoPlayersForSeconds(int numberOfSeconds) {
		System.out.println("Publishing after " + numberOfSeconds + " seconds");
	}
}
