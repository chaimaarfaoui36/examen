package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.model.Password;

public class UsersService implements UserDetailsService {
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	private UsersDao usersDao = new UsersDao();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Criterion crit = Restrictions.eq("email", username);
		List<Users> users = usersDao.findByCriteria(crit);
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

	public MessageResponse changePasswrod(Password password) {

		Criterion crit = Restrictions.eq("email", password.getEmail());
		List<Users> users = usersDao.findByCriteria(crit);

		if (users.isEmpty()) {
			return new MessageResponse(false, "Utilisateur introuvable");
		}
		Users user = users.get(0);
		boolean valid = bCryptPasswordEncoder.matches(password.getOldPassword(), user.getPassword());

		if (!valid) {
			return new MessageResponse(false, "Ancien mot de passe inccorect");
		}

		String cryptedPassword = bCryptPasswordEncoder.encode(password.getNewPassword());

		user.setPassword(cryptedPassword);
		usersDao.update(user);

		return new MessageResponse(true, "Opération effectuée avec succès");

	}

}
