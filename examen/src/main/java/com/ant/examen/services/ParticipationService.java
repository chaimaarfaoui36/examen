package com.ant.examen.services;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ParticipationDao;
import com.ant.examen.dao.ReponseCandidatDao;
import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.ReponseCandidat;

public class ParticipationService {

	private ParticipationDao participationDao = new ParticipationDao();
	private ReponseCandidatDao reponseCandidatDao = new ReponseCandidatDao();
	private QuestionService questionService = new QuestionService();
	private ReponseService reponseService = new ReponseService();

	public Participation save(Participation participation) {
		Criterion crit1 = Restrictions.eq("candidat", participation.getCandidat());
		Criterion crit2 = Restrictions.eq("examen", participation.getExamen());
		Criterion crit = Restrictions.and(crit1, crit2);
		List<Participation> list = participationDao.findByCriteria(crit);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		participation.setDateParticipation(new Date());
		participationDao.save(participation);

		// reponse candidat
		List<Question> questions = questionService.findByExamen(participation.getExamen());

		for (Question quest : questions) {

			List<Reponse> reponses = reponseService.findByQuestion(quest);

			for (Reponse rep : reponses) {

				crit1 = Restrictions.eq("participation", participation);
				crit2 = Restrictions.eq("reponse", rep);
				crit = Restrictions.and(crit1, crit2);

				List<ReponseCandidat> reponseCandidats = reponseCandidatDao.findByCriteria(crit);
				if (reponseCandidats.isEmpty()) {
					ReponseCandidat reponseCandidat = new ReponseCandidat();
					reponseCandidat.setParticipation(participation);
					reponseCandidat.setReponse(rep);
					reponseCandidatDao.save(reponseCandidat);
				}
			}

		}

		return participation;

	}
}
