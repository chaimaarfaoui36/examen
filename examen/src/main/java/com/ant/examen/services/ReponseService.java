package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ReponseDao;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;

public class ReponseService {

	private ReponseDao reponseDao = new ReponseDao();

	public List<Reponse> findByQuestion(Question question) {

		Criterion crit = Restrictions.eq("question", question);
		return reponseDao.findByCriteria(crit);
	}
}
