package nl.dpa.geos.event.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.dpa.geos.event.util.UserUtil;
import nl.dpa.geos.event.dao.UserDao;
import nl.dpa.geos.event.model.User;

@Service
public class UserServiceImpl implements UserService{
    
	@Autowired
	private UserDao userDao;
	
	@Override
	public User createUser(User user) {
		user.setPassword(UserUtil.getPasswordHash(user.getPassword()));
	    User newUser = userDao.save(user);
	    newUser.setPassword(null);
		return newUser;
	}

}
