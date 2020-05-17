package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Users;

public class UsersService implements UserDetailsService {

	private UsersDao UsersDao = new UsersDao();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Criterion crit = Restrictions.eq("email", username);
		List<Users> users = UsersDao.findByCriteria(crit);
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

}
