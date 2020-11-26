package com.ae5.sige.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ae5.sige.encryption.Encriptacion;
import com.ae5.sige.exception.UserNotFound;
import com.ae5.sige.model.Usuario;
import com.ae5.sige.repository.UsuarioRepositoryInt;



@Service("UsuarioServiceInt")
/**
 * @author ae5
 */
@Transactional

public class UsuarioService implements UsuarioServiceInt {
  /**
   * @author ae5
   */
  private UsuarioRepositoryInt userRepository;

  /**
   * @author ae5
   */
  @Autowired

  public UsuarioService(final UsuarioRepositoryInt userRepository) {

    this.userRepository = userRepository;

  }

  /**
   * @author ae5
   */
  public Usuario findByUsernusuario(final String dni) {
	
	  
    final Optional<Usuario> user = userRepository.findOne(dni);
    
    if (user.isPresent()) {	
    	 final Optional<Usuario> userDesencriptado = Encriptacion.desencriptarOptionalUsuario(user);

         return userDesencriptado.get();
    } else {

        throw new UserNotFound(dni);

      }
    
    
  }
  
  /**
   * @author ae5
   */
  public Usuario findByUsernusuarioencriptado(final String dni) {
	
	  
    final Optional<Usuario> user = userRepository.findOne(dni);
    
    if (user.isPresent()) {	
         return user.get();
    } else {

        throw new UserNotFound(dni);

      }
    
    
  }


  /**
   * @author ae5
   */
  public List<Usuario> findAll() {
    final Optional<List<Usuario>> users = userRepository.findAll();
    return Encriptacion.desencriptarListaUsuarios(users);         
  }

  /**
   * @author ae5
   */
  public void saveUsuario(final Usuario usuario) {

    userRepository.saveUsuario(usuario);

  } 

  /**
   * @author ae5
   */
  public void updateUsuario(final Usuario user) {

    userRepository.updateUsuario(user);

  }

  /**
   * @author ae5
   */
  public void deleteUsuario(final String nusuario) {

    userRepository.deleteUsuario(nusuario);

  }
  /**e
   * @author ae5
   */

  public Usuario getUserBynusuarioAndPassword(final String dni, final String password) {
    return userRepository.findByDniAndContrasena(dni, password);
  }
  /**
   * @author ae5
   */

  public List<String> findReuniones(String dni) {
		
	return userRepository.getReuniones(dni);
  }


}
