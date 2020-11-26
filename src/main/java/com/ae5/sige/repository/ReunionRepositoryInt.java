package com.ae5.sige.repository;

import java.util.List;
import java.util.Optional;

import com.ae5.sige.model.Reunion;


public interface ReunionRepositoryInt {
	 /**
	   * Metodo que te devuelve todas las Reuniones.
	   * 
	   * @author ae5
	   */
	  Optional<List<Reunion>> findAll();

	  /**
	   * Metodo para guardar un Reunion.
	   * 
	   * @author ae5
	   */
	  void saveReunion(Reunion reunion);

	  /**
	   * Metodo para actualizar una Reunion.
	   * 
	   * @author ae5
	   */
	  void updateReunion(Reunion reunion);

	  /**
	   * Metodo para borrar una Reunion.
	   * 
	   * @author ae5
	   */
	  void deleteReunion(String reunion);

	  /**
	   * Metodo para obtener una Reunion por su id.
	   * 
	   * @author ae5
	   */
	  Optional<Reunion> findOne(String id);
	  
	  /**
	   * Obtener un usuario en función de su dni.
	   * 
	   * @author ae5
	   */
	  List<Reunion> findUsuario(String dni);


}
