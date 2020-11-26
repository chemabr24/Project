package com.ae5.sige.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ae5.sige.model.Reunion;

import com.ae5.sige.repository.ReunionRepositoryInt;




@Service("ReunionServiceInt")
/**
 * @author ae5
 */
@Transactional

public class ReunionService implements ReunionServiceInt{

  /**
   * @author ae5
   */
  private ReunionRepositoryInt reunionRepository;

  /**
   * @author ae5
   */
  @Autowired

  public ReunionService(final ReunionRepositoryInt reunionRepository) {
    this.reunionRepository = reunionRepository;
  }

  /**
   * @author ae5
   * @throws Exception 
   */
  @Override
  public Reunion findByReunionId(String id) throws Exception {
	  Optional<Reunion> reunion = reunionRepository.findOne(id);
	    if (reunion.isPresent()) {

	      return reunion.get();
	    } else {
	      throw new NoSuchElementException();
	    }
   
  }

  /**
   * @author ae5
   */
  @Override
  public List<Reunion> findAll() {
    final Optional<List<Reunion>> reuniones = reunionRepository.findAll();
    return reuniones.get();
  }

  /**
   * @author ae5
   */
  public void saveReunion(final Reunion reunion) {
	  
	  reunionRepository.saveReunion(reunion);
  } 

  /**
   * @author ae5
   */
  public void updateReunion(final Reunion reunion) {
	  reunionRepository.updateReunion(reunion);
  }

  /**
   * @author ae5
   */
  public void deleteReunion(final String userId) {
	  reunionRepository.deleteReunion(userId);
  }
  
  public List<Reunion> getReunionesByUsuario(String dni) {
	    return reunionRepository.findUsuario(dni);
	  }

}
