package nl.dpa.geos.event.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.dpa.geos.event.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer>{
	
	User findByUserName(String username);

}
