package br.com.cl.automation.core.driver;

import br.com.cl.automation.core.exception.CoreException;

public class DriverManagerFactory {

	private DriverManagerFactory() {
	}

	private static DriverManager driverManager;
	private static DriverType driverType;

	public static DriverManager getManager(DriverType type) {

		if (checkIfManagerOfTypeIsCreated(type)) {
			return driverManager;
		}

		tearDownCurrentManagerIfExistent();

		switch (type) {
		case CHROME:
			driverManager = new ChromeManager();
			driverType = DriverType.CHROME;
			break;
		case FIREFOX:
			driverManager = new FirefoxManager();
			driverType = DriverType.FIREFOX;
			break;
		case ANDROID:
			driverManager = new AndroidManager();
			driverType = DriverType.ANDROID;
			break;
		default:
			throw new CoreException("Manager not found");
		}
		return driverManager;

	}

	public static void tearDownCurrentManagerIfExistent() {
		if (driverManager != null) {
			driverManager.tearDown();
		}
	}

	private static boolean checkIfManagerOfTypeIsCreated(DriverType type) {
		return (driverType == type && driverManager != null);
	}
}
