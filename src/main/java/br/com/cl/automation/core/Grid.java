package br.com.cl.automation.core;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.DefaultCapabilityMatcher;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;
import org.openqa.grid.shared.GridNodeServer;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.server.SeleniumServer;

import lombok.extern.java.Log;

@Log
public class Grid {

	private static Hub hub;
	private static SelfRegisteringRemote remote;
	private static GridNodeServer node;

	private Grid() {
		throw new IllegalStateException("Utility class");
	}

	public static void nodeStart() {
		try {
			GridNodeConfiguration gnc = new GridNodeConfiguration();

			gnc.register = true;
			gnc.hub = String.format("http://%s:%d", hub.getConfiguration().host, hub.getConfiguration().port);
			gnc.port = 5555;
			gnc.proxy = DefaultRemoteProxy.class.getCanonicalName();
			gnc.maxSession = 1;
			gnc.cleanUpCycle = 2000;
			gnc.registerCycle = 0;
			gnc.unregisterIfStillDownAfter = 10000;
			gnc.role = GridRole.NODE.toString();
			gnc.port = 5555;
			gnc.hub = String.format("http://%s:%d", hub.getConfiguration().host, hub.getConfiguration().port);

			RegistrationRequest request = RegistrationRequest.build(gnc);
			node = new SeleniumServer(request.getConfiguration());
			remote = new SelfRegisteringRemote(request);
			remote.setRemoteServer(node);
			remote.startRemoteServer();
			remote.startRegistrationProcess();

		} catch (Exception e) {
			log.severe(e.getMessage());
		}
	}

	public static void hubStart() {
		GridHubConfiguration ghc = new GridHubConfiguration();
		ghc.host = "localhost";
		ghc.port = 4444;
		ghc.capabilityMatcher = new DefaultCapabilityMatcher();

		hub = new Hub(ghc);
		hub.start();
	}

	public static void stopNode() {
		remote.stopRemoteServer();
		node.stop();
	}

	public static void hubStop() {
		hub.stop();
		System.exit(0);
	}
}
