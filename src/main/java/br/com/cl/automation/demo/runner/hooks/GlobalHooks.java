package br.com.cl.automation.demo.runner.hooks;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.cl.automation.core.driver.DriverManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalHooks {

	@BeforeClass
	public static void startGrid() {
		log.info("BeforeClass");
	}

	@AfterClass
	public static void finish() {
		log.info("AfterClass");
		DriverManagerFactory.tearDownCurrentManagerIfExistent();
	}
}
