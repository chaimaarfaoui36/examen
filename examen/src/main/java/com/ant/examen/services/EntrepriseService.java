package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.EntrepriseDao;
import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;

public class EntrepriseService {
	private EntrepriseDao entrepriseDao = new EntrepriseDao();
	private UsersDao usersDao = new UsersDao();

	public MessageResponse register(Entreprise entreprise) {

		Criterion crit = Restrictions.eq("email", entreprise.getEmail());
		List<Users> list = usersDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return new MessageResponse(false, "Email existe déja");
		}

		entreprise.setEnabled(true);
		entrepriseDao.save(entreprise);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public List<Entreprise> findAll() {
		return entrepriseDao.findAll();
	}

	public MessageResponse activate(Entreprise entreprise) {
		entreprise.setEnabled(!entreprise.isEnabled());
		entrepriseDao.update(entreprise);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}
}
