package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ReponseCandidatDao;
import com.ant.examen.dao.ReponseDao;
import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.ReponseCandidat;

public class ReponseCandidatService {
	
	private ReponseCandidatDao reponseCandidatDao = new ReponseCandidatDao();
	
	public void updateSingleResponse(ReponseCandidat reponseCandidat) {
		List<ReponseCandidat> reponseCandidats = 
				reponseCandidatDao.findByCriteria(Restrictions.idEq(reponseCandidat.getId()));
		if(!reponseCandidats.isEmpty()) {
			reponseCandidat = reponseCandidats.get(0);
		}
		 reponseCandidats = findByQuestion(reponseCandidat.getReponse().getQuestion(), reponseCandidat.getParticipation());
		
		reponseCandidats.forEach(rep -> {
			rep.setEtat(false);
			reponseCandidatDao.update(rep);
		});
			
			
		
			reponseCandidat.setEtat(true);
			reponseCandidatDao.update(reponseCandidat);
		
	}
	
	
	
	public void updateMultiResponse(ReponseCandidat reponseCandidat) {
	
			reponseCandidatDao.update(reponseCandidat);
		
	}


	public List<ReponseCandidat> findByQuestion(Question question, Participation participation) {
		return reponseCandidatDao.findByQuestion(question, participation);
	}
}
