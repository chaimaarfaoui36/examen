package com.ant.examen.services;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ParticipationDao;
import com.ant.examen.entities.Participation;

public class ParticipationService {

	private ParticipationDao participationDao = new ParticipationDao();

	public Participation save(Participation participation) {
		Criterion crit1 = Restrictions.eq("candidat", participation.getCandidat());
		Criterion crit2 = Restrictions.eq("examen", participation.getExamen());
		Criterion crit = Restrictions.and(crit1, crit2);
		List<Participation> list = participationDao.findByCriteria(crit);
		
		if (!list.isEmpty()) {
			return list.get(0);
		}

		participation.setDateParticipation(new Date());
		participationDao.save(participation);
		
		// reponse candidat 

		return participation;

	}
}
