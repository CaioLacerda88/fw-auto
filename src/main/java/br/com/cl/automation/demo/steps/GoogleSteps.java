package br.com.cl.automation.demo.steps;

import org.junit.Assert;

import br.com.cl.automation.core.BaseSteps;
import br.com.cl.automation.core.PageMaker;
import br.com.cl.automation.core.driver.DriverType;
import br.com.cl.automation.demo.page.GooglePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps extends BaseSteps {

	GooglePage page;

	public GoogleSteps() {
		super(DriverType.CHROME);
		page = PageMaker.getInstance(GooglePage.class, driver);
	}

	@Given("I navigate to google")
	public void given() {
		web.navigateToUrl(GooglePage.HTTP_GOOGLE_COM);
	}

	@When("I search for {string}")
	public void when(String word) {
		web.typeIntoField(page.getQuery(), word);
		web.elementSubmit(page.getQuery());
	}

	@Then("I validate the outcome")
	public void then() {
		web.elementIsClickable(page.getResult(), 5);
		Assert.assertTrue("Text null", page.getResult().isDisplayed());
	}

}
