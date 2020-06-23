package com.ant.examen.dao;

import java.util.Date;
import java.util.List;

import com.ant.examen.entities.Examen;

public class ExamenDao extends GenericDao<Examen> {

	public ExamenDao() {
		super(Examen.class);
		// TODO Auto-generated constructor stub
	}

	public List<Examen> findByThemeAndSte(List<Integer> themes, List<Integer> stes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e inner join e.questionExamens qe "
						+ " inner join qe.question q "
						+ " where e.entreprise.id in(:stes) " 
						+ " and e.dateExpiration>=:date "
						+ " and q.theme.id in(:themes) group by e.id")
				.setParameterList("stes", stes).setParameterList("themes", themes)
				.setParameter("date", new Date()).list();

		hibernateSession.close();

		return list;
	}

	public List<Examen> findByTheme(List<Integer> themes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e inner join e.questionExamens qe "
						+ " inner join qe.question q"
						+ " where  e.dateExpiration>=:date "
						+ " and q.theme.id in(:themes) group by e.id")

				.setParameterList("themes", themes)
				.setParameter("date", new Date()).list();

		hibernateSession.close();

		return list;
	}

	public List<Examen> findBySte(List<Integer> stes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e  " 
						+ "   where e.dateExpiration>=:date "
						+ " and e.entreprise.id in(:stes)  group by e.id")
				.setParameterList("stes", stes)
				.setParameter("date", new Date()).list();

		hibernateSession.close();

		return list;
	}

}
