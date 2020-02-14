package br.com.cl.automation.core;

import org.openqa.selenium.WebDriver;

import br.com.cl.automation.core.driver.DriverManager;
import br.com.cl.automation.core.driver.DriverManagerFactory;
import br.com.cl.automation.core.driver.DriverType;
import br.com.cl.automation.core.utils.StoredActions;

public abstract class BaseSteps {

	protected DriverManager driverManager;
	protected WebDriver driver;
	protected StoredActions web;

	protected BaseSteps(DriverType type) {
		driverManager = DriverManagerFactory.getManager(type);
		driver = driverManager.getDriver();
		web = new StoredActions(driver);
	}

}
