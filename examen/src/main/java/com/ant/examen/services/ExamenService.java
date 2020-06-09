package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ExamenDao;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
import com.ant.examen.model.MessageResponse;

public class ExamenService {

	private ExamenDao examenDao = new ExamenDao();

	public MessageResponse save(Examen examen) {

		examenDao.save(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public MessageResponse delete(Examen examen) {

		Criterion crit1 = Restrictions.idEq(examen.getId());
		Criterion crit2 = Restrictions.isNotEmpty("participations");
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Examen> examens = examenDao.findByCriteria(crit3);
		if (!examens.isEmpty()) {
			return new MessageResponse(false, "Existe un ou plusieurs participtation des candidat");
		}
		examenDao.delete(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}
	public MessageResponse update(Examen examen) {

		Criterion crit1 = Restrictions.idEq(examen.getId());
		Criterion crit2 = Restrictions.isNotEmpty("participations");
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Examen> examens = examenDao.findByCriteria(crit3);
		if (!examens.isEmpty()) {
			return new MessageResponse(false, "Existe un ou plusieurs participtation des candidat");
		}
		examenDao.update(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}
	
	
	public List<Examen> findByEntreprise(Entreprise entreprise) {
		Criterion crit = Restrictions.eq("entreprise", entreprise);
		return examenDao.findByCriteria(crit);
	}

	public Examen findById(Integer idQ) {
		Criterion crit = Restrictions.idEq(idQ);
		List<Examen> examens = examenDao.findByCriteria(crit );
		if(!examens.isEmpty()) {
			return examens.get(0);
		}
		return null;
	}
}
