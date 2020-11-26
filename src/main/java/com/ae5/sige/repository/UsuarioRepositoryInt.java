package com.ae5.sige.repository;

import java.util.List;
import java.util.Optional;

import com.ae5.sige.model.Usuario;

public interface UsuarioRepositoryInt {
	
	/**
	   * Método que te devuelve todas las reuniones de un usuario.
	   * 
	   * @author ae5
	   */
	List<String> getReuniones(String dni);

	/**
	 * Método que te devuelve todos los usuarios.
	 * 
	 * @author ae5
	 */
	Optional<List<Usuario>> findAll();

	/**
	 * Método para guardar un usuario.
	 * 
	 * @author ae5
	 */
	void saveUsuario(Usuario usuario);

	/**
	 * Método para actualizar un usuario.
	 * 
	 * @author ae5
	 */
	void updateUsuario(Usuario usuario);

	/**
	 * Método para borrar un usuario.
	 * 
	 * @author ae5
	 */
	void deleteUsuario(String usuario);

	/**
	 * Método para obtener un usuario por su id.
	 * 
	 * @author ae5
	 */
	Optional<Usuario> findOne(String nusuario);

	/**
	 * Método para obtener un usuario por su nombre de usuario y contraseña.
	 * 
	 * @author ae5
	 */
	Usuario findByDniAndContrasena(String dni, String contrasena);


}
