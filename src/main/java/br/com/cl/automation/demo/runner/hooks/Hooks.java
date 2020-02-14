package br.com.cl.automation.demo.runner.hooks;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

	private static final String DIVIDER = "-----------------------";

	@Before
	public void setup(Scenario s) {
		log.info(DIVIDER);
		log.info("Executing scenario: '" + s.getName() + "'");
		log.info(DIVIDER);
	}

	@After
	public void tearDown(Scenario s) {
		log.info(DIVIDER);
		log.info("Scenario result: '" + s.getStatus() + "'");
		log.info(DIVIDER);
	}

}
