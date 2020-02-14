package br.com.cl.automation.demo.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.cl.automation.core.PageMaker;
import lombok.Getter;

@Getter
public class GooglePage implements PageMaker {

	public static final String HTTP_GOOGLE_COM = "http://google.com";

	@FindBy(xpath = "//input[@name='q']")
	private WebElement query;

	@FindBy(xpath = "//div[@class='FPdoLc VlcLAe']//input[@name='btnK']")
	private WebElement searchButton;

	@FindBy(xpath = "//div[@id='resultStats']")
	private WebElement result;

}
