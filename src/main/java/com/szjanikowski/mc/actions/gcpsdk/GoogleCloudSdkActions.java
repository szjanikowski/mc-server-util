package com.szjanikowski.mc.actions.gcpsdk;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.compute.v1.InstanceClient;
import com.google.cloud.compute.v1.InstanceSettings;
import com.google.cloud.compute.v1.ProjectZoneInstanceName;
import com.szjanikowski.mc.ZeroPeriodExceededAction;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Requires(env = Environment.GOOGLE_COMPUTE)
public class GoogleCloudSdkActions implements ZeroPeriodExceededAction {

	private static final Logger LOG = LoggerFactory.getLogger(GoogleCloudSdkActions.class);

	private final String project;
	private final String zone;
	private final String instance;

	volatile InstanceClient instanceClient;

	public GoogleCloudSdkActions(
			@Property(name="mcutil.gcp.project") String project,
			@Property(name="mcutil.gcp.zone") String zone,
			@Property(name="mcutil.gcp.instance") String instance) {
		this.project = project;
		this.zone = zone;
		this.instance = instance;
	}

	@PostConstruct
	void init() throws IOException {
		this.instanceClient = createInstanceClient();
		LOG.info("Successfully initialized instance client for GCP");
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
	public void zeroPlayersPeriodOf(int minutes) {
		LOG.info("Stopping instance after " + minutes + " minutes of zero players!");
		if (instanceClient != null) {
			instanceClient.stopInstance(ProjectZoneInstanceName.newBuilder()
					.setProject(project)
					.setZone(zone)
					.setInstance(instance)
					.build());
		} else {
			LOG.error("Cannot stop! Not initialized instance client properly");
		}

	}
}
