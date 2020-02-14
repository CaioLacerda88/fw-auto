package br.com.cl.automation.core.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.cl.automation.core.exception.CoreException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidManager extends DriverManager {

	@Override
	void createDriver() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("deviceName", "Samsung Tablet");
		desiredCapabilities.setCapability("udid", "6caaa559");
//		desiredCapabilities.setCapability("deviceName", "0045967777");
		desiredCapabilities.setCapability("automationName", "uiautomator2");
//		desiredCapabilities.setCapability("appPackage", "com.wt.apkinfo");
//		desiredCapabilities.setCapability("appActivity", ".activity.StartActivity");
		desiredCapabilities.setCapability("appPackage", "br.com.santander.microcredito.prd");
		desiredCapabilities.setCapability("appActivity", ".MainActivity");
		try {
			webDriver = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
		} catch (MalformedURLException e) {
			throw new CoreException(e);
		}

		webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

}
