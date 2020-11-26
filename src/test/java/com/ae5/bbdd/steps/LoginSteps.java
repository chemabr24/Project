package com.ae5.bbdd.steps;
import static org.junit.Assert.assertEquals;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.ae5.sige.repository.UsuarioRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

  WebDriver driver=null;
  UsuarioRepository ur;
  List<Map<String, String>> a;
  String testCase="";

  @Given("abrir la aplicación web en el navegador")
  public void abrir_la_aplicacion_web_en_el_navegador() {

	  Path path = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe");       
	    System.setProperty("webdriver.gecko.driver", path.toString());
    FirefoxOptions fo = new FirefoxOptions();
    fo.addArguments("--headless");
    driver = new FirefoxDriver(fo);

    driver.get("http://localhost:4200");
  }
  

  @When("introducir los datos de acceso")
  public void introducir_los_datos_de_acceso(DataTable dataTable) {

    a = dataTable.asMaps(String.class, String.class);

    driver.findElement(By.xpath("//input[@placeholder='DNI']")).sendKeys(a.get(0).get("DNI"));
    driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys(a.get(0).get("password"));
    testCase = a.get(0).get("testCase");
  }

  @Then("pulsamos entrar")
  public void pulsamos_entrar() throws InterruptedException {

    driver.findElement(By.xpath("//input[@value='Acceder']")).click();
    String expectedUrl = driver.getCurrentUrl();

    if (testCase.equals("Case1")||testCase.equals("Case2")) {
      assertEquals("http://localhost:4200/reuniones", expectedUrl);
    } else {
      assertEquals("http://localhost:4200/login", expectedUrl);
    }

    Thread.sleep(7000);
    driver.close();

  }
}