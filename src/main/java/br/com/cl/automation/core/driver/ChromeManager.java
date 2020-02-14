package br.com.cl.automation.core.driver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import br.com.cl.automation.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeManager extends DriverManager {

	@Override
	void createDriver() {
		WebDriverManager.chromedriver().setup();
		try {
//			webDriver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), setupChromeOptions());
			webDriver = new ChromeDriver(setupChromeOptions());
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	private static ChromeOptions setupChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.LOGGING_PREFS, createLogsSetup());
		options.setCapability("applicationCacheEnabled", false);
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--test-name", "--test-type", "--no-check-default-driver", "--reduce-security-for-testing",
				"--allow-running-insecure-content", // Aceita certificados SSL self-signed
				"--disable-web-security", // Permite chamadas Ajax CORS
				"--no-sandbox", // Permite executar chrome dentro de um container docker
				"--no-first-run", // Evita que o chrome abra popups na inicialização
				"--disable-popup-blocking", "--disable-infobars", "--testing-fixed-https-port", "--start-maximized",
				"--disable-extensions");
		return options;
	}

	private static LoggingPreferences createLogsSetup() {
		LoggingPreferences loggingPreferences = new LoggingPreferences();
		loggingPreferences.enable(LogType.DRIVER, Level.SEVERE);
		return loggingPreferences;
	}

	@Override
	public void tearDown() {
		super.tearDown();
		try {
			Runtime.getRuntime().exec("wmic process where name=\"chromedriver.exe\" call terminate");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
