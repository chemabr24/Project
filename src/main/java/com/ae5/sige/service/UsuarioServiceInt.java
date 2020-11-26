package com.ae5.sige.service;

import java.util.List;

import com.ae5.sige.model.Usuario;

public interface UsuarioServiceInt {

	/**
	   * @author ae5
	   */
	  List<Usuario> findAll();

	  /**
	   * @author ae5
	   */
	  Usuario findByUsernusuario(String dni);

	  /**
	   * @author ae5
	   */
	  void saveUsuario(Usuario usuario);

	  /**
	   * @author ae5
	   */
	  void updateUsuario(Usuario usuario);

	  /**
	   * @author ae5
	   */
	  void deleteUsuario(String dni);

	  /**
	   * @author ae5
	   */
	  Usuario getUserBynusuarioAndPassword(String nusuario, String password);
	  
	  List<String> findReuniones(String dni);
	  
	  Usuario findByUsernusuarioencriptado(String dni);
 
	

}
