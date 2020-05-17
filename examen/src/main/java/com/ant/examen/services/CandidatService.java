package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.CandidatDao;
import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;

public class CandidatService {

	private CandidatDao candidatDao = new CandidatDao();
	private UsersDao usersDao = new UsersDao();

	public MessageResponse register(Candidat candidat) {

		Criterion crit = Restrictions.eq("email", candidat.getEmail());
		List<Users> list = usersDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return new MessageResponse(false, "Email existe déja");
		}
		Criterion crit2 = Restrictions.eq("cin", candidat.getCin());
		List<Candidat> list2 = candidatDao.findByCriteria(crit2);

		if (!list2.isEmpty()) {
			return new MessageResponse(false, "CIN existe déja");
		}
		candidat.setEnabled(true);
		candidatDao.save(candidat);

		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public List<Candidat> findAll() {
		return candidatDao.findAll();
	}

	public MessageResponse activate(Candidat candidat) {
		candidat.setEnabled(!candidat.isEnabled());
		candidatDao.update(candidat);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}
}
