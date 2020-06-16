package com.ant.examen.dao;

import java.util.List;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.QuestionExamen;
import com.ant.examen.entities.Theme;

public class QuestionExamenDao  extends GenericDao<QuestionExamen>{

	public QuestionExamenDao() {
		super(QuestionExamen.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<QuestionExamen> findByThemeExamen(Theme theme, Examen examen) {
		
		startOperation();
		
	List<QuestionExamen> list	=hibernateSession.createQuery("select qe from QuestionExamen qe inner join qe.question q    where qe.examen=:examen"
				+ " and q.theme=:theme")
		.setParameter("examen", examen).setParameter("theme", theme).list();
		hibernateSession.close();
		
		return list;
	}

}
