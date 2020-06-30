package com.ant.examen.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.ReponseCandidat;

public class ReponseCandidatDao extends GenericDao<ReponseCandidat> {

	public ReponseCandidatDao() {
		super(ReponseCandidat.class);
		// TODO Auto-generated constructor stub
	}

	public List<ReponseCandidat> findByQuestion(Question question, Participation participation) {

		startOperation();

		List<ReponseCandidat> reps = hibernateSession.createCriteria(ReponseCandidat.class, "rc")
				.createAlias("rc.reponse", "r")
				.add(Restrictions.eq("r.question", question))
				.add(Restrictions.eq("rc.participation", participation)).list();

		hibernateSession.close();
		return reps;
	}

}
