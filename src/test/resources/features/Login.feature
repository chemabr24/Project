Feature: Acceder al sistema

  Scenario Outline: Accediendo al sistema de gestión de reuniones
    Given abrir la aplicación web en el navegador
    When introducir los datos de acceso
      | DNI   | password   | testCase   |
      | <DNI> | <password> | <testCase> |
    Then pulsamos entrar

    Examples: 
      | DNI       | password       | testCase |
      | 05722902L | Contraseña1    | Case1    |
      | 08765345R | proyectoiso873 | Case2    |
      | 05722902L | fdasfa6214     | Case3    |
      | 08765345R |                | Case4    |
      |           | proyectoiso873 | Case5    |
