package com.ant.examen.dao;

import java.util.Date;
import java.util.List;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Theme;

public class ExamenDao extends GenericDao<Examen> {

	public ExamenDao() {
		super(Examen.class);
		// TODO Auto-generated constructor stub
	}

	public List<Examen> findByThemeAndSte(List<Integer> themes, List<Integer> stes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e inner join e.questionExamens qe " + " inner join qe.question q "
						+ " where e.entreprise.id in(:stes) " + " and e.dateExpiration>=:date "
						+ " and q.theme.id in(:themes) group by e.id")
				.setParameterList("stes", stes).setParameterList("themes", themes).setParameter("date", new Date())
				.list();

		hibernateSession.close();

		return list;
	}

	public List<Examen> findByTheme(List<Integer> themes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e inner join e.questionExamens qe " + " inner join qe.question q"
						+ " where  e.dateExpiration>=:date " + " and q.theme.id in(:themes) group by e.id")

				.setParameterList("themes", themes).setParameter("date", new Date()).list();

		hibernateSession.close();

		return list;
	}
	
	
	public List<Examen> findByTheme(Theme theme, Entreprise entreprise) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e inner join e.questionExamens qe " + " inner join qe.question q"
						+ " where  e.entreprise =:entreprise " + " and q.theme =:theme group by e.id")

				.setParameter("theme", theme)
				.setParameter("entreprise", entreprise)
				.list();

		hibernateSession.close();

		return list;
	}

	public List<Examen> findBySte(List<Integer> stes) {

		startOperation();

		List<Examen> list = hibernateSession
				.createQuery("select e from Examen e  " + "   where e.dateExpiration>=:date "
						+ " and e.entreprise.id in(:stes)  group by e.id")
				.setParameterList("stes", stes).setParameter("date", new Date()).list();

		hibernateSession.close();

		return list;
	}

	public List<Examen> findByMonth(int month) {

		startOperation();

		List<Examen> list = hibernateSession.createQuery("select e from Examen e  "
				+ "   where Month(e.dateCreation)=:month " + " and YEAR(e.dateCreation) = YEAR(CURRENT_DATE)")
				.setParameter("month", month).list();

		hibernateSession.close();

		return list;
	}
	
	public List<Examen> findByMonth(int month, Entreprise entreprise) {

		startOperation();

		List<Examen> list = hibernateSession.createQuery("select e from Examen e  "
				+ "   where e.entreprise=:entreprise and Month(e.dateCreation)=:month " + " and YEAR(e.dateCreation) = YEAR(CURRENT_DATE)")
				.setParameter("month", month)
				.setParameter("entreprise", entreprise).list();

		hibernateSession.close();

		return list;
	}
}
