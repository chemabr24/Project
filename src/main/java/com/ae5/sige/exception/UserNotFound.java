package com.ae5.sige.exception;

import org.springframework.core.NestedRuntimeException;

public class UserNotFound extends NestedRuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Este método muestra el mensaje de excepción de usuario no encontrado.
   */
  public UserNotFound(String nusuario) {

    super(String.format("Usuario con  nombre de usuario '%s' no ha sido enocntrado", nusuario));

  }

}
