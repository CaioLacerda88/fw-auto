package br.com.cl.automation.core.utils;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.cl.automation.core.exception.StoredActionsException;
import lombok.extern.java.Log;

@Log
public class StoredActions {

	private WebDriver driver;

	private static final Long MEDIUMMILLIS = 10000l;
	private static final Long LONGMILLIS = 20000l;
	private static final Integer DEFAULTPOLLINGSECONDS = 1;
	private static final Integer DEFAULTTIMEOUTSECONDS = 10;
	private static final Duration D10SECONDS = Duration.ofSeconds(10);
	private static final Duration D500MILLIS = Duration.ofMillis(500);

	public StoredActions(WebDriver driver) {
		this.driver = driver;
	}

	public void registerStep(String message) {
		// verify implementation of test-report-generator
		((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

	public void navigateToUrl(String url) {
		driver.get(url);
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public String getHTML() {
		return driver.getPageSource();
	}

	public void typeIntoField(WebElement element, String text) {
		getDefaultFluentWait(3l, 250l).until(ExpectedConditions.elementToBeClickable(element));
		element.sendKeys(text);
	}

	public WebDriverWait getWait(Integer seconds) {
		return new WebDriverWait(driver, seconds);
	}

	public FluentWait<WebDriver> getDefaultFluentWait(Long timeoutInSeconds, Long pollingTimeInMillis) {
		return new FluentWait<>(driver) //
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)) //
				.pollingEvery(Duration.ofMillis(pollingTimeInMillis)) //
				.ignoring(NoSuchElementException.class) //
				.ignoring(TimeoutException.class);
	}

	private WebElement fluentlyWaitUntilClickable(WebElement element, Integer timeoutInSeconds,
			Integer pollingInSeconds) {
		return (new FluentWait<WebDriver>(driver)) //
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)) //
				.pollingEvery(Duration.ofSeconds(pollingInSeconds)) //
				.ignoring(StaleElementReferenceException.class) //
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	private WebElement explicitlyWaitForElementToBeClickable(WebElement element, Integer segundos) {
		return new WebDriverWait(driver, segundos) //
				.ignoring(NoSuchElementException.class) //
				.ignoring(StaleElementReferenceException.class) //
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	private WebElement fluentlyWaitUntilClickable(WebElement element) {
		return fluentlyWaitUntilClickable(element, DEFAULTTIMEOUTSECONDS, DEFAULTPOLLINGSECONDS);
	}

	private void sleep(Long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void click(WebElement elemento) {
		try {
			JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
			jsExecutor.executeScript("arguments[0].click();", elemento);
		} catch (StaleElementReferenceException ex) {
			elemento.click();
		} catch (WebDriverException ex) {
			this.sleep(MEDIUMMILLIS);
			elemento.click();
			log.warning(ex.getMessage());
		}
	}

	public void selectRadioFromList(List<WebElement> elements, Integer option) {
		if (option >= 0 && option <= elements.size()) {
			WebElement elemento = elements.get(option);
			this.click(elemento);
		}
	}

	public void selectElementByIndex(WebElement element, Integer index) {
		try {
			Select dropdown = new Select(fluentlyWaitUntilClickable(element));
			dropdown.selectByIndex(index);
		} catch (Exception ex) {
			log.severe("Erro ao selecionar op��o por index.");
		}
	}

	public void selectElementByText(WebElement element, String value) {
		try {
			Select dropdown = new Select(this.fluentlyWaitUntilClickable(element));
			dropdown.selectByVisibleText(value);
		} catch (Exception ex) {
			log.severe("Erro ao selecionar opcao por texto.");
		}
	}

	public void selectElementByValue(WebElement element, String value) {
		try {

			Select dropdown = new Select(this.fluentlyWaitUntilClickable(element));
			dropdown.selectByValue(value);
		} catch (Exception ex) {
			log.severe("Erro ao selecionar opcao por texto.");
		}
	}

	public void insertText(WebElement element, String value) {
		this.fluentlyWaitUntilClickable(element).sendKeys(value);
	}

	public void insertKey(WebElement element, Keys key) {
		this.fluentlyWaitUntilClickable(element).sendKeys(key);
	}

	public void clickOnElement(WebElement element) {
		fluentlyWaitUntilClickable(element).click();
	}

	public void sendEscToElement(WebElement element) {
		this.fluentlyWaitUntilClickable(element, 10, 1).sendKeys(Keys.ESCAPE);
	}

	public WebElement searchElementByText(String text) {
		this.sleep(LONGMILLIS);
		return driver.findElement(By.xpath(String.format("//*[contains(text(),'%1$s')]", text)));
	}

	public void clearElement(WebElement element) {
		fluentlyWaitUntilClickable(element).clear();
	}

	public Boolean elementIsVisible(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException ex) {
			log.severe("Elemento n�o vis�vel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException ex) {
			log.severe("Elemento n�o vis�vel (StaleElementReferenceException).");
			return false;
		} catch (Exception ex) {
			log.severe("Elemento n�o vis�vel " + ex.getMessage());
			return false;
		}
	}

	public Boolean elementIsClickable(WebElement element, Integer segundos) {
		try {
			return explicitlyWaitForElementToBeClickable(element, segundos) != null;
		} catch (NoSuchElementException ex) {
			log.severe("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException ex) {
			log.severe("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception ex) {
			log.severe(("Elemento nao visivel " + ex.getMessage()));
			return false;
		}
	}

	public Boolean elementIsClickable(WebElement element) {
		return elementIsClickable(element, 1);
	}

	public void elementSubmit(WebElement element) {
		element.submit();
	}

	public Boolean elementIsEnabled(WebElement element) {
		return element.isEnabled();
	}

	public Boolean waitForElementToBeEnabled(WebElement element) {
		return fluentlyWaitUntilClickable(element).isEnabled();
	}

	public String getTextFromElement(WebElement element) {
		return fluentlyWaitUntilClickable(element).getText();
	}

	public Boolean waitForElementToBeDisplayed(WebElement element) {
		return fluentlyWaitUntilClickable(element).isDisplayed();
	}

	public void lowerTimeouts() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
	}

	public void raiseTimeouts() {
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public String getSourceFromPage() {
		return driver.getPageSource();
	}

	public String getUrlFromPage() {
		return driver.getCurrentUrl();
	}

	public Boolean isDisplayed(By element) {
		Boolean isDisplayed = true;

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(element).isDisplayed();
		} catch (NoSuchElementException e) {
			isDisplayed = false;
		} finally {
			raiseTimeouts();
		}

		return isDisplayed;
	}

	public Boolean isDisplayed(WebElement webelement, Integer segundosAteTimeout) {
		lowerTimeouts();
		Boolean isDisplayed = false;
		Integer contadorSegundos = 0;
		try {
			while (contadorSegundos < segundosAteTimeout && !isDisplayed) {
				if (webelement.isDisplayed() && webelement.getSize().getWidth() > 0
						&& webelement.getSize().getHeight() > 0) {
					isDisplayed = true;
				}
				getWait(1);
				contadorSegundos++;
			}
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			isDisplayed = false;
		}
		raiseTimeouts();
		return isDisplayed;
	}

	public Boolean isTextEmpty(WebElement element) {
		return readFromElement(element).isEmpty();
	}

	public void clickListItem(List<WebElement> elements, String valor) {
		WebElement listItem = getListItem(elements, valor);
		if (listItem == null) {
			throw new StoredActionsException("Element not found");
		}
		listItem.click();
	}

	public WebElement getListItem(List<WebElement> elements, String valor) {
		for (WebElement element : elements) {
			if (element.isDisplayed() && element.getText().equals(valor)) {
				return element;
			}
		}
		return null;
	}

	public void sendTab(WebElement element) {
		element.sendKeys(Keys.TAB);
	}

	// ********************* Selecao ************************
	public void moveToElement(WebElement webElement) {
		new Actions(driver).moveToElement(webElement);
	}

	public Boolean isSelected(WebElement element) {
		return element.isSelected();
	}
	// ********************* Textos e Atributos ************************

	public String readFromElement(WebElement element) {
		return element.getText();
	}

	public void writeToElement(WebElement element, String texto) {
		element.clear();
		element.sendKeys(texto);
	}

	public String getAttribute(WebElement element, String atributo) {
		return element.getAttribute(atributo);
	}

	// ********************* JS ************************

	public Object executeJS(String cmd, Object... param) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(cmd, param);
	}

	// ********************* Clipboard ************************

	public String getStringFromClipboard() {
		String text = null;
		try {
			text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			log.severe(e.getMessage());
		}
		return text;
	}

	public void hoverOverElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();

	}

	// ********************* Regex ************************

	public String reduceMultipleSpacesToOne(String text) {
		String clean = text;
		Pattern pattern = Pattern.compile("(.*)(\\S)(\\s{2,})(\\S)(.*)");
		Matcher matcher = pattern.matcher(clean);
		while (matcher.matches()) {
			String inicio = matcher.group(1);
			String meio1 = matcher.group(2);
			String meio2 = matcher.group(4);
			String fim = matcher.group(5);
			clean = inicio + meio1 + " " + meio2 + fim;
			matcher = pattern.matcher(clean);
		}
		return clean;
	}

	// ********************* Espera ************************

	private void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000l);
		} catch (InterruptedException e) {
			log.severe(e.getMessage());
			Thread.currentThread().interrupt();
		}

	}

	public Boolean waitForElementToBeSelected(WebElement element) {
		getWait(30).until(ExpectedConditions.elementSelectionStateToBe(element, true));
		return isSelected(element);
	}

	public WebElement waitElementToBeClickable(WebElement element) {
		return getWait(30).withTimeout(D10SECONDS).pollingEvery(D500MILLIS)
				.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitElementToBeClickable(WebElement element, Integer segundosAteTimeout) {
		return getWait(30).withTimeout(Duration.ofSeconds(segundosAteTimeout)).pollingEvery(D500MILLIS)
				.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitVisibilityOf(WebElement element, Integer segundosAteTimeout) {
		return getWait(segundosAteTimeout).until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitVisibilityOf(WebElement element) {
		return waitVisibilityOf(element, 10);
	}

	public Boolean waitForElementToDissapear(WebElement element) {
		return waitForElementToDissapear(element, 5);
	}

	public Boolean waitForElementToDissapear(WebElement element, Integer secondsTimeout) {
		lowerTimeouts();
		Boolean dissapeared = false;
		Integer counter = 0;
		try {
			while (counter < secondsTimeout && !dissapeared) {
				if (!element.isDisplayed()
						|| (element.getSize().getWidth() == 0 && element.getSize().getHeight() == 0)) {
					dissapeared = true;
				}
				sleep(1);
				counter++;
			}
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			dissapeared = true;
		}
		raiseTimeouts();
		return dissapeared;
	}

}