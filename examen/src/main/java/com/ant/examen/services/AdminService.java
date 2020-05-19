package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.AdminstrateurDao;
import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Administrateur;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;

public class AdminService {
	private AdminstrateurDao adminDao = new AdminstrateurDao();
	private UsersDao usersDao = new UsersDao();
	public MessageResponse update(Administrateur admin) {
		Criterion crit1 = Restrictions.idEq(admin.getId());
		Criterion crit2 = Restrictions.eq("email", admin.getEmail());
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Users> list = usersDao.findByCriteria(crit3);
		if (list.isEmpty()) {
			list = usersDao.findByCriteria(crit2);
			if (list.isEmpty()) {
				return new MessageResponse(false, "Email existe déja");
			}
		}
		

		adminDao.update(admin);

		return new MessageResponse(true, "Opération effectuée avec succès");

	}
	
	public Administrateur findByEmail(String email) {
		Criterion crit = Restrictions.eq("email", email);
		List<Administrateur> list = adminDao.findByCriteria(crit );
		
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
		
	}
	
}
