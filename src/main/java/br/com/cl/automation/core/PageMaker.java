package br.com.cl.automation.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.cl.automation.core.exception.PageException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public interface PageMaker {
	public static <T extends PageMaker> T getInstance(Class<T> pageClass, WebDriver driver) {
		try {
			T pageObject = pageClass.getDeclaredConstructor().newInstance();
			if (driver instanceof AppiumDriver) {
				PageFactory.initElements(new AppiumFieldDecorator(driver), pageObject);
			} else {
				PageFactory.initElements(driver, pageObject);
			}
			return pageObject;
		} catch (Exception e) {
			throw new PageException(e);
		}
	}
}