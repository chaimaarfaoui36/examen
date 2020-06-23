package com.ant.examen.dao;

import java.util.List;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Theme;

public class ThemeDao extends GenericDao<Theme> {

	public ThemeDao() {
		super(Theme.class);
		// TODO Auto-generated constructor stub
	}

	public List<Theme> findByExamen(Examen examen) {
		startOperation();
		List<Theme> list = hibernateSession.createQuery("select t from Theme t "
				+ " inner join t.questions q inner join q.questionExamens qe " + " where qe.examen=:exam group by t.id")
				.setParameter("exam", examen).list();
		hibernateSession.close();
		return list;
	}

}
