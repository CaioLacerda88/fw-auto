package br.com.cl.automation.core.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import br.com.cl.automation.core.exception.CoreException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxManager extends DriverManager {

	@Override
	void createDriver() {
		WebDriverManager.firefoxdriver().setup();
		try {
			webDriver = new FirefoxDriver(new FirefoxOptions());
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

}
