package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ant.examen.dao.CandidatDao;
import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Administrateur;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;

public class CandidatService {
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
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

		String cryptedPwd = bCryptPasswordEncoder.encode(candidat.getPassword());
		candidat.setPassword(cryptedPwd);
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

	public MessageResponse update(Candidat candidat) {
		Criterion crit1 = Restrictions.idEq(candidat.getId());
		Criterion crit2 = Restrictions.eq("email", candidat.getEmail());
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Users> list = usersDao.findByCriteria(crit3);
		if (list.isEmpty()) {
			list = usersDao.findByCriteria(crit2);
			if (list.isEmpty()) {
				return new MessageResponse(false, "Email existe déja");
			}
		}
		crit2 = Restrictions.eq("cin", candidat.getCin());
		crit3 = Restrictions.and(crit1, crit2);
		List<Candidat> list2 = candidatDao.findByCriteria(crit3);
		if (list2.isEmpty()) {
			list2 = candidatDao.findByCriteria(crit2);
			if (!list2.isEmpty()) {
				return new MessageResponse(false, "CIN existe déja");
			}
		}

		candidatDao.update(candidat);

		return new MessageResponse(true, "Opération effectuée avec succès");

	}
	
	
	public Candidat findByEmail(String email) {
		Criterion crit = Restrictions.eq("email", email);
		List<Candidat> list = candidatDao.findByCriteria(crit );
		
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
		
	}

}
