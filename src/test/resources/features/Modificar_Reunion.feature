Feature: Modificar reunion

  Scenario: Modificacion de una reunion

  Scenario Outline: <testCase> <expectedResult>
    Given el usuario se loguea en el sistema y quiere cambiar los campos de la reunion para actualizarlos
      | Titulo   | Descripcion   | Fecha   | Hora inicio   | Hora fin   | Lista asistentes   |
      | <Titulo> | <Descripcion> | <Fecha> | <Hora inicio> | <Hora fin> | <Lista asistentes> |
    When el usuario modifica los campos de una reunion
      | Titulo   | Descripcion   | Organizador   | Fecha   | Hora inicio   | Hora fin   | Lista asistentes   |
      | <Titulo> | <Descripcion> | <Organizador> | <Fecha> | <Hora inicio> | <Hora fin> | <Lista asistentes> |
    Then la reunion es modificada y se actualiza en la bbdd '<expectedResult>'

    Examples: 
      | testCase                | expectedResult | Titulo           | Descripcion                                    | Fecha      | Hora inicio | Hora fin | Lista asistentes |
      | No Titulo presente      | FAILS          |                  | Reunion donde veremos como abordar el proyecto | 25/11/2020 | 12:00       | 13:50    | 08765345R        |
      | No Descripcion presente | FAILS          | Control proyecto |                                                | 25/11/2020 | 12:00       | 13:50    | 08765345R        |
      | No Fecha presente       | FAILS          | Control proyecto | Reunion donde veremos como abordar el proyecto |            | 12:00       | 13:50    | 08765345R        |
      | No Hora inicio presente | FAILS          | Control proyecto | Reunion donde veremos como abordar el proyecto | 25/11/2020 |             | 13:50    | 08765345R        |
      | No Hora fin presente    | FAILS          | Control proyecto | Reunion donde veremos como abordar el proyecto | 25/11/2020 | 12:00       |          | 08765345R        |
      | En el resto de casos    | IS SUCCESFUL   | Control proyecto | Reunion donde veremos como abordar el proyecto | 25/11/2020 | 12:00       | 13:50    | 08765345R        |
