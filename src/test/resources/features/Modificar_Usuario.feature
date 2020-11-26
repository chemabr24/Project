Feature: Modificar Usuario

  Scenario: Modificacion de usuario

  Scenario Outline: <testCase> <expectedResult>
    Given loguear y modificar
    When el usuario se modifica los valores
      | DNI   | Contraseña   | Nombre   | Apellidos   | E-mail   | Nº Telefono  |
      | <dni> | <contraseña> | <nombre> | <apellidos> | <e-mail> | <n_telefono> |
    Then se modifica los valores del usuario y se sustituye en la base de datos '<expectedResult>'

    Examples: 
      | testCase              | expectedResult | DNI       | Contraseña | Nombre | Apellidos         | E-mail            | Nº Telefono |
      | contraseña incorrecta | FAILS          | 05227738M | hjy        | Julian | Moraga Martin     | jurugar@gmail.com |   654345670 |
      | e-mail incorrecto     | FAILS          | 05227738M | 1q2w3e4r   | Julian | Gomez Asencio     | jurugar@gmail     |   654345670 |
      | n-telefono incorrecto | FAILS          | 05227738M | 1q2w3e4r   | Julian | Rodriguez Maestre | jurugar@gmail.com |    65434567 |
      | En el resto de casos  | IS SUCCESFUL   | 05227738M | 1q2w3e4r   | Julian | Ruiz Garcia       | jurugar@gmail.com |   654345670 |
