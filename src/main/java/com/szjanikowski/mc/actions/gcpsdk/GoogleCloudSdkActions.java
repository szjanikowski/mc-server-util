package com.szjanikowski.mc.actions.gcpsdk;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.compute.v1.InstanceClient;
import com.google.cloud.compute.v1.InstanceSettings;
import com.szjanikowski.mc.ZeroPeriodExceededAction;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Requires(env = Environment.GOOGLE_COMPUTE)
public class GoogleCloudSdkActions implements ZeroPeriodExceededAction {

	volatile InstanceClient instanceClient;

	@PostConstruct
	void init() throws IOException {
		this.instanceClient = createInstanceClient();
		System.out.println("Successfully initialized instance client for GCP");
	}

	private InstanceClient createInstanceClient() throws IOException {
		Credentials myCredentials = GoogleCredentials.getApplicationDefault();
		String myEndpoint = InstanceSettings.getDefaultEndpoint();

		InstanceSettings instanceSettings =
				InstanceSettings.newBuilder()
						.setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
						.setTransportChannelProvider(
								InstanceSettings.defaultHttpJsonTransportProviderBuilder()
										.setEndpoint(myEndpoint)
										.build())
						.build();
		return InstanceClient.create(instanceSettings);
	}

	@Override
	public void zeroPlayersPeriodExceededBy(int seconds) {
		System.out.println("Period exceeded by " + seconds + " seconds");
		if (instanceClient != null) {
			instanceClient.stopInstance("8652063237409753691");
		} else {
			System.out.println("Cannot stop! Not initialized instance client properly");
		}

	}
}
