package br.com.cl.automation.core.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	protected WebDriver webDriver;

	public WebDriver getDriver() {
		if (webDriver == null) {
			createDriver();
		}
		return webDriver;
	}

	abstract void createDriver();

	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
	}
}
