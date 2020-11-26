package com.ae5.bbdd.steps;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.ae5.sige.repository.UsuarioRepository;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class Eliminar_UsuarioSteps {

  WebDriver driver;
  DataTable dataTable;
  List<Map<String, String>> a;
  UsuarioRepository usuarioRepository;
  
  @Given("el administrador quiere eliminar a un usuario registrado")
  public void el_administrador_quiere_eliminar_a_un_usuario_registrado(
  DataTable dataTable) {

	  
	  Path path = FileSystems.getDefault().getPath("src/test/resources/features/geckodriver.exe");
	    System.setProperty("webdriver.gecko.driver", path.toString());
	    FirefoxOptions fo = new FirefoxOptions();
	    fo.addArguments("--headless");
	    WebDriver driver = new FirefoxDriver(fo);
	    driver.get("http://localhost:4200/auth/login");

	    a = dataTable.asMaps(String.class, String.class);

	    driver.findElement(By.xpath("//input[@placeholder='DNI']")).sendKeys(a.get(0).get("Usuario"));
	    driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys(a.get(0).get("contraseña"));
	    driver.findElement(By.xpath("//input[@value='Acceder']")).click();
	  }

  

  @When("el administrador  eliminar� el usuario seleccionado")
  public void el_administrador_eliminara_el_usuario_seleccionado() {


   driver.findElement(By.xpath("//input[@value='Usuario']")).click();

    /* SELECCIONAR ELEMENTO DE LA TABLA DE LISTA */

    driver.findElement(By.xpath("//input[@value='Eliminar Usuario']")).click();

  }

  @Then(" se elimina el usuario  de la base de datos")
  public void se_elimina_el_usuario_de_la_base_de_datos(String string) {

    /* Comprobar que el usuario  que voy a borrar esta en la base de datos */
    driver.findElement(By.xpath("//input[@value='Eliminar']")).click();
    /* Comprobar que se ha eliminado */

    driver.close();
  }

}
