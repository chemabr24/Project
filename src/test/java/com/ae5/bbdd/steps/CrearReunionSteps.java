package com.ae5.bbdd.steps;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.ae5.sige.service.ReunionService;
import com.ae5.sige.service.ReunionServiceInt;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.ae5.sige.model.Reunion;
import com.ae5.sige.service.ReunionService;

import java.util.Optional;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CrearReunionSteps {
	  WebDriver driver=null;
	  List<Map<String, String>> a;
	  String testCase="";
	  String titulo="";
	

	  @Given ("se abre el formulario del login")
	  public void se_abre_el_formulario_del_login_y_el_usuario_introduce_sus_credenciales() {
		Path path = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe");       
		System.setProperty("webdriver.gecko.driver", path.toString());
	    FirefoxOptions fo = new FirefoxOptions();
	    fo.addArguments("--headless");
	    driver = new FirefoxDriver(fo);

	    driver.get("http://localhost:4200");
	    

	  }
	  
	  @When ("el usuario introduce sus credenciales y va a crear una reunion con los siguientes datos")
	  public void el_usuario_introduce_sus_credenciales_y_va_a_crear_una_reunion_con_los_siguientes_datos(DataTable dataTable) {
		a = dataTable.asMaps(String.class, String.class);

		driver.findElement(By.xpath("//input[@placeholder='DNI']")).sendKeys(a.get(0).get("DNI"));
		driver.findElement(By.xpath("//input[@placeholder='Contraseña']")).sendKeys(a.get(0).get("password"));    
		driver.findElement(By.xpath("//input[@value='Acceder']")).click();
		
		driver.findElement(By.xpath("//li[2]/a")).click();
		driver.findElement(By.id("titulo")).sendKeys(a.get(0).get("titulo"));
	    
		driver.findElement(By.id("descripcion")).sendKeys(a.get(0).get("descripcion"));
		driver.findElement(By.id("fecha")).sendKeys(a.get(0).get("fecha"));
		driver.findElement(By.id("horaIni")).sendKeys(a.get(0).get("horainicio"));
		driver.findElement(By.id("horaFin")).sendKeys(a.get(0).get("horafin"));
		testCase = a.get(0).get("testCase");
		titulo =a.get(0).get("titulo");
	   
	  }
	  
	  @Then ("pulsamos crear una reunion")
	  public void pulsamos_crear_una_reunion() {
		  driver.findElement(By.cssSelector("div:nth-child(7) > .btn-primary")).click();
//		  System.out.println(ReunionService); es nulo
//		  Optional<Reunion> reu =   ReunionService.findOneTitulo(titulo);
//		  if(testCase.equals("testCase1")) {
//			  assertNotEquals(reu.get(),null);
//		  }else {
//			  assertEquals(reu.get(),null);
//		  }
		  driver.close();
	  }

}
