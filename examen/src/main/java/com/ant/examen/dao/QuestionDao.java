package com.ant.examen.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Question;

public class QuestionDao extends GenericDao<Question> {

	public QuestionDao() {
		super(Question.class);
		// TODO Auto-generated constructor stub
	}

	public List<Question> findByExamen (Examen examen) {
		
		
		startOperation();
		
		List<Question> list = hibernateSession.
				createCriteria(Question.class, "quest")
				.createAlias("quest.questionExamens", "questEx")
				.add(Restrictions.eq("questEx.examen", examen))
				.list();
		hibernateSession.close();
		return list;
	}
}
