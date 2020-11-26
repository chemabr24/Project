package com.ae5.sige.service;

import java.util.List;

import com.ae5.sige.model.Reunion;

public interface ReunionServiceInt {
	  /**
	   * @author ae5
	   */
	  List<Reunion> findAll();

	  /**
	   * @author ae5
	   * @throws Exception 
	   */
	  Reunion findByReunionId(String id) throws Exception;

	  /**
	   * @author ae5
	   */
	  void saveReunion(Reunion reunion);

	  /**
	   * @author ae5
	   */
	  void updateReunion(Reunion reunion);

	  /**
	   * @author ae5
	   */
	  void deleteReunion(String id);
	  
}
