package com.ae5.bbdd.steps; 
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
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


public class Registro {

  WebDriver driver=null;
  UsuarioRepository ur;
  List<Map<String, String>> a;
  String testCase="";

  @Given("registro")
  public void registro() throws MalformedURLException {

	  Path path = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe");       
	    System.setProperty("webdriver.gecko.driver", path.toString());
  FirefoxOptions fo = new FirefoxOptions();
  fo.addArguments("--headless");
  driver = new FirefoxDriver(fo);

  driver.get("http://localhost:4200/register");

  }

  @When("introducir los datos de registro")
  public void introducir_los_datos_de_registro(DataTable dataTable) {

    a = dataTable.asMaps(String.class, String.class);

    driver.findElement(By.xpath("//input[@placeholder='dni']")).sendKeys(a.get(0).get("DNI"));
    driver.findElement(By.xpath("//input[@placeholder='contraseña']")).sendKeys(a.get(0).get("password"));
    driver.findElement(By.xpath("//input[@placeholder='Repita su contraseña']")).sendKeys(a.get(0).get("password"));
    driver.findElement(By.xpath("//input[@placeholder='nombre']")).sendKeys(a.get(0).get("nombre"));
    driver.findElement(By.xpath("//input[@placeholder='apellidos']")).sendKeys(a.get(0).get("apellidos"));
    driver.findElement(By.xpath("//input[@placeholder='correo']")).sendKeys(a.get(0).get("correo"));
    driver.findElement(By.xpath("//input[@placeholder='telefono']")).sendKeys(a.get(0).get("telefono"));
   

  }

  @Then("pulsamos REGISTRARSE")
  public void pulsamos_REGISTRARSE() {

    driver.findElement(By.xpath("//input[@value='Registrar']")).click();
    String expectedUrl = driver.getCurrentUrl();
    
    if (testCase.equals("Case1")) {
        assertEquals("http://localhost:4200/login", expectedUrl);
      } else {
        assertEquals("http://localhost:4200/register", expectedUrl);
      }
//    if (a.get(0).get("resultado") == "Registro Correcto") {
//      assertEquals(a.get(0).get("dni"), this.ur.findOne(a.get(0).get("dni")));
//      this.ur.deleteUsuario(a.get(0).get("dni"));
//    }

    driver.close();
  }

}
