Feature: Crear Reunion

Scenario Outline: Se crea una reunión y se guarda en el sistema

Given se abre el formulario del login
When  el usuario introduce sus credenciales y va a crear una reunion con los siguientes datos
|DNI	|password		|titulo		|descripcion	|organizador	|fecha	|horainicio		|horafin	|listaasistentes	|testCase		|
|<DNI>|<password>	|<titulo>	|<descripcion>|<organizador>|<fecha>|<horainicio>	|<horafin>|<listaasistentes>|<testCase>	|
Then pulsamos crear una reunion
		
Examples:
|DNI			|password				|titulo						|descripcion																		|organizador|fecha			|horainicio	|horafin|listaasistentes|testCase	|
|08765345R|proyectoiso873	|Control proyecto	|Reunion donde veremos como abordar el proyecto	|08765345R	|25/11/2020	|12:00			|13:50	|08765345R			|testCase1|
