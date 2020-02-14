package br.com.cl.automation.demo.runner;

import org.junit.runner.RunWith;

import br.com.cl.automation.demo.runner.hooks.GlobalHooks;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@CucumberOptions(features = "classpath:features", //
		glue = "br.com.cl", //
		tags = "@google", //
		plugin = { "json:target/cucumber-report/cucumber.json" }, //
		monochrome = true, //
		snippets = SnippetType.CAMELCASE //
)
@RunWith(Cucumber.class)
public class RunnerTest extends GlobalHooks {

}
