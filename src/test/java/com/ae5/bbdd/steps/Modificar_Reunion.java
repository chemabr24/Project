package com.ae5.bbdd.steps; 

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


public class Modificar_Reunion {

  WebDriver driver;

  UsuarioRepository usuarioRepository;

  List<Map<String, String>> a;

  @Given("el usuario se loguea en el sistema y quiere cambiar los campos de la reunion para actualizarlos")
  public void el_usuario_se_loguea_en_el_sistema_y_quiere_cambiar_los_campos_de_la_reunion_para_ctualizarlos(DataTable dataTable) {

    Path path = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe");
    System.setProperty("webdriver.gecko.driver", path.toString());
    FirefoxOptions fo = new FirefoxOptions();
    fo.addArguments("--headless");
    WebDriver driver = new FirefoxDriver(fo);
    driver.get("http://localhost:4200/login");
    a = dataTable.asMaps(String.class, String.class);

    driver.findElement(By.xpath("//input[@placeholder='DNI']")).sendKeys(a.get(0).get("DNI"));
    driver.findElement(By.xpath("//input[@placeholder='password']")).sendKeys(a.get(0).get("password"));
    driver.findElement(By.xpath("//input[@value='Acceder']")).click();
  }

  @When("el usuario modifica los campos de una reunion")
  public void el_usuario_modifica_los_campos_de_una_reunion( DataTable dataTable) {
    
  

    a = dataTable.asMaps(String.class, String.class);
    

  }

  @Then("la reunion es modificada y se actualiza en la bbdd {string}")
  public void la_reunion_es_modificada_y_se_actualiza_en_la_bbdd(String string) {
    driver.findElement(By.xpath("//input[@value='Modificar']")).click();
 
    driver.close();  
  }
}
