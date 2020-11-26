package com.ae5.sige.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ae5.sige.model.Reunion;



@Repository
public class ReunionRepository implements ReunionRepositoryInt{
	 /**
	   * Instancia de la interfaz MongoOperations.
	   * 
	   * @author ae5
	   */
	  private final MongoOperations mongoOperations;

	  /**
	   * Constructor de la clase.
	   * 
	   * @author ae5
	   */
	  @Autowired

	  public ReunionRepository(final MongoOperations mongoOperations) {
	    Assert.notNull(mongoOperations, "notNull");
	    this.mongoOperations = mongoOperations;

	  }

	  /**
	   * Devuelve todas las reuniones.
	   * 
	   * @author ae5
	   */
	  public Optional<List<Reunion>> findAll() {

	    List<Reunion> reuniones = this.mongoOperations.findAll(Reunion.class);
	    return Optional.ofNullable(reuniones);

	  }

	  /**
	   * Devuelve una Reunion en función de su id.
	   * 
	   * @author ae5
	   */
	  public Optional<Reunion> findOne(final String id) {
		    Reunion d = this.mongoOperations.findOne(new Query(Criteria.where("_id").is(id)), Reunion.class);
		    return Optional.ofNullable(d);
		  }

	  /**
	   * Guarda una Reunion en la base de datos.
	   * 
	   * @author ae5
	   */
	  public void saveReunion(final Reunion reunion) {
	    this.mongoOperations.save(reunion);
	  }

	  /**
	   * Actualiza una Reunion en la base de datos.
	   * 
	   * @author ae5
	   */
	  public void updateReunion(final Reunion reunion) {

	    this.mongoOperations.save(reunion);
	  }

	  /**
	   * Borra un Reunion en la base de datos.
	   * 
	   * @author ae5
	   */
	  public void deleteReunion(final String id) {

	    this.mongoOperations.findAndRemove(new Query(Criteria.where("id").is(id)), Reunion.class);

	  }
	  
	  @Override
	  public List<Reunion> findUsuario(final String dni) {
		    final List<Reunion> reuniones = this.mongoOperations
		        .find(new Query(Criteria.where("listaAsistentes").is(dni)), Reunion.class);
		   return reuniones;
		  }
}
