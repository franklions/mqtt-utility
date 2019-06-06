package org.eclipse.paho.sample.mqttclient.mqttv3;

import java.net.URISyntaxException;

import org.apache.commons.cli.CommandLine;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttV3Connection {

	private String hostURI;
	private String clientID;
	private MqttConnectOptions conOpts = new MqttConnectOptions();
	private boolean automaticReconnect = false;

	/**
	 * Initialises an MQTTv3 Connection Object which holds the configuration
	 * required to open a connection to an MQTTv3.1.1 server
	 * 
	 * @param cliOptions
	 *            - The command cliOptions options to parse.
	 * @throws URISyntaxException
	 */
	public MqttV3Connection(CommandLine cliOptions) throws URISyntaxException {

		// Get the Host URI
		if (cliOptions.hasOption("host")) {
			hostURI = cliOptions.getOptionValue("host");
		}

		if (cliOptions.hasOption("id")) {
			clientID = cliOptions.getOptionValue("id");
		}

		if (cliOptions.hasOption("keepalive")) {
			conOpts.setKeepAliveInterval(Integer.parseInt(cliOptions.getOptionValue("keepalive")));
		}

		if (cliOptions.hasOption("password")) {
			conOpts.setPassword(cliOptions.getOptionValue("password").toCharArray());
		}
		if (cliOptions.hasOption("username")) {
			conOpts.setUserName(cliOptions.getOptionValue("username"));
		}
		if (cliOptions.hasOption("will-payload") && cliOptions.hasOption("will-topic")) {
			String willPayload = cliOptions.getOptionValue("will-payload");
			String willTopic = cliOptions.getOptionValue("will-topic");
			int qos = 0;
			boolean retained = false;
			if (cliOptions.hasOption("will-qos")) {
				qos = Integer.parseInt(cliOptions.getOptionValue("will-qos"));
			}
			if (cliOptions.hasOption("will-retain")) {
				retained = true;
			}
			conOpts.setWill(willTopic, willPayload.getBytes(), qos, retained);
		}

		if (cliOptions.hasOption("clean-session")) {
			conOpts.setCleanSession(true);
		}

		if (cliOptions.hasOption("max-inflight")) {
			conOpts.setMaxInflight(Integer.parseInt(cliOptions.getOptionValue("max-inflight")));
		}
		if (cliOptions.hasOption("automatic-reconnect")) {
			conOpts.setAutomaticReconnect(true);
			this.automaticReconnect = true;
		}
		
		
		// If the client ID was not set, generate one ourselves
		if (clientID == null || clientID == "") {
			// No client ID provided, generate one from the process ID
			long pid = Thread.currentThread().getId(); //ProcessHandle.current().pid();
			clientID = "mqtt-client-" + pid;
		}

	}

	public String getHostURI() {
		return hostURI;
	}

	public String getClientID() {
		return clientID;
	}

	public MqttConnectOptions getConOpts() {
		return conOpts;
	}
	
	public boolean isAutomaticReconnectEnabled() {
		return this.automaticReconnect;
	}

}
