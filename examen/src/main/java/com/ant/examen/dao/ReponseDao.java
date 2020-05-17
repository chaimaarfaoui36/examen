package com.ant.examen.dao;

import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;

public class ReponseDao extends GenericDao<Reponse> {

	public ReponseDao() {
		super(Reponse.class);
		// TODO Auto-generated constructor stub
	}
	 
	public void deleteByQuestion(Question question) {
		startOperation();
	
		hibernateSession.createQuery("delete from Reponse r where r.question =:question")
		.setParameter("question", question).executeUpdate();
		endOperation();
	}

}
