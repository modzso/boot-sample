package org.core.boot.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aqs")
public class AqsProperties {

	private static final Logger LOG = LoggerFactory.getLogger(AqsProperties.class);

	private String serverName;
	private int serverPort;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		LOG.debug("Set serverName: {}", serverName);
		this.serverName = serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		LOG.debug("Set serverName: {}", serverPort);
		this.serverPort = serverPort;
	}

	@Override
	public String toString() {
		return "AqsProperties [serverName=" + serverName + ", serverPort="
				+ serverPort + "]";
	}


}
