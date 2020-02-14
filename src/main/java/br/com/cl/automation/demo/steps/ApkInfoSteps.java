package br.com.cl.automation.demo.steps;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.support.ui.ExpectedConditions;

import br.com.cl.automation.core.BaseSteps;
import br.com.cl.automation.core.PageMaker;
import br.com.cl.automation.core.driver.DriverType;
import br.com.cl.automation.demo.page.ApkInfoPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApkInfoSteps extends BaseSteps {

	ApkInfoPage page;

	public ApkInfoSteps() {
		super(DriverType.ANDROID);
		page = PageMaker.getInstance(ApkInfoPage.class, driver);
	}

	@Dado("que eu abra a atividade principal")
	public void abrirAtividadePrincipal() {
	}

	@Quando("eu verificar seu carregamento")
	public void verificarCarregamento() {
		web.getDefaultFluentWait(10l, 250l).until(ExpectedConditions.visibilityOf(page.getTitle()));
	}

	@Então("verifico o texto dos elementos")
	public void textoDosElementos() {
		(page.getAllHeaders()).stream().forEach(log::info);
	}

	@Então("verifico o texto do titulo")
	public void telaInicial() {
		assertEquals("Apk Info", page.getTitle().getText());
	}

}
