package br.com.cl.automation.demo.page;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import br.com.cl.automation.core.PageMaker;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;

@Getter
public class ApkInfoPage implements PageMaker {
	@AndroidFindBy(id = "text1")
	List<WebElement> headers;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Apk Info']")
	WebElement title;

	public List<String> getAllHeaders() {
		return headers.stream().map(WebElement::getText).collect(Collectors.toList());
	}
}
