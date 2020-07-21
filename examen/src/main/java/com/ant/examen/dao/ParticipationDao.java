package com.ant.examen.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Participation;

public class ParticipationDao extends GenericDao<Participation> {

	public ParticipationDao() {
		super(Participation.class);
		// TODO Auto-generated constructor stub
	}

	public List<Participation> findByMonth(int month) {

		startOperation();
		List<Participation> list = list = hibernateSession.createQuery("select e from Participation e  "
				+ "   where Month(e.dateParticipation)=:month " + " and YEAR(e.dateParticipation) = YEAR(CURRENT_DATE)")
				.setParameter("month", month).list();

		hibernateSession.close();

		return list;

	}

	public List<Participation> findByMonth(int month, Entreprise entreprise) {

		startOperation();
		List<Participation> list = list = hibernateSession
				.createQuery("select e from Participation e  " + "   where e.examen.entreprise=:entreprise and "
						+ " Month(e.dateParticipation)=:month " + " and YEAR(e.dateParticipation) = YEAR(CURRENT_DATE)")
				.setParameter("month", month).setParameter("entreprise", entreprise).list();

		hibernateSession.close();

		return list;

	}

	public List<Participation> findByScore(Criterion criterion, Entreprise entreprise) {

		startOperation();
		List<Participation> list = hibernateSession.createCriteria(Participation.class, "part")
				.createAlias("part.examen", "ex").add(criterion).add(Restrictions.eq("ex.entreprise", entreprise))
				.list();
		hibernateSession.close();
		return list;
	}
	
	
	
}
