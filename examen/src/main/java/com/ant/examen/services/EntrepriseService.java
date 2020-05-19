package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ant.examen.dao.EntrepriseDao;
import com.ant.examen.dao.UsersDao;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Users;
import com.ant.examen.model.MessageResponse;

public class EntrepriseService {
	private EntrepriseDao entrepriseDao = new EntrepriseDao();
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	private UsersDao usersDao = new UsersDao();

	public MessageResponse register(Entreprise entreprise) {

		Criterion crit = Restrictions.eq("email", entreprise.getEmail());
		List<Users> list = usersDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return new MessageResponse(false, "Email existe déja");
		}

		entreprise.setEnabled(true);

		String cryptedPwd = bCryptPasswordEncoder.encode(entreprise.getPassword());
		entreprise.setPassword(cryptedPwd);
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
	
	
	
	
	public MessageResponse update(Entreprise entreprise) {
		Criterion crit1 = Restrictions.idEq(entreprise.getId());
		Criterion crit2 = Restrictions.eq("email", entreprise.getEmail());
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Users> list = usersDao.findByCriteria(crit3);
		if (list.isEmpty()) {
			list = usersDao.findByCriteria(crit2);
			if (list.isEmpty()) {
				return new MessageResponse(false, "Email existe déja");
			}
		}
		

		entrepriseDao.update(entreprise);

		return new MessageResponse(true, "Opération effectuée avec succès");

	}
	
	
	public Entreprise findByEmail(String email) {
		Criterion crit = Restrictions.eq("email", email);
		List<Entreprise> list = entrepriseDao.findByCriteria(crit );
		
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
		
	}
	
}
