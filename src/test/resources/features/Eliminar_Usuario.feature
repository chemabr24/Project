Feature: Eliminar usuario

  Scenario: Eliminacion de usuario

  Scenario Outline: <testCase> <expectedResult>
    Given el administrador quiere eliminar a un usuario registrado
      | DNI | Contraseña | Nombre | Apellidos | E-mail | Nº Telefono |
    When el administrador  eliminará el usuario seleccionado
      | DNI   | Contraseña   | Nombre   | Apellidos   | E-mail   | Nº Telefono  |
      | <dni> | <contraseña> | <nombre> | <apellidos> | <e-mail> | <n_telefono> |
    Then se elimina el usuario  de la base de datos. '<expectedResult>'

    Examples: 
      | testCase | expectedResult | DNI       | Contraseña | Nombre | Apellidos     | E-mail           | Nº Telefono |  |
      | Case1    | SUCCESS        | 09887534S | vdsafdas23 | Jorge  | Gomez Sanchez | jorge2@gmail.com |   676543212 |  |
