package com.ant.examen.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ParticipationDao;
import com.ant.examen.dao.ReponseCandidatDao;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
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

	public Participation findById(Integer idQ) {
		Criterion crit = Restrictions.idEq(idQ);

		List<Participation> list = participationDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public void finshParticipation(Participation participation) {
		participation.setFinished(true);
		participationDao.update(participation);

	}

	public List<Participation> findByCandidat(Candidat candidat) {
		Criterion crit = Restrictions.eq("candidat", candidat);
		// TODO Auto-generated method stub
		return participationDao.findByCriteria(crit);
	}

	public List<Participation> findByExamen(Examen examen) {
		Criterion crit = Restrictions.eq("examen", examen);
		// TODO Auto-generated method stub List<User> sortedList = users.stream()
		List<Participation> list = participationDao.findByCriteria(crit);

		List<Participation> sortedList = list.stream()
				.sorted(Comparator.comparingDouble(Participation::getScore).reversed()).collect(Collectors.toList());
		return sortedList;
	}

	public List<Participation> findByMonth(int month) {
		return participationDao.findByMonth(month);
	}

	public List<Participation> findByMonth(int month, Entreprise entreprise) {
		return participationDao.findByMonth(month, entreprise);
	}

	public List<Number> findScore(Entreprise entreprise) {

		List<Number> list = new ArrayList<>();

		Criterion crit = Restrictions.lt("score", 0.25);

		List<Participation> l = participationDao.findByScore(crit, entreprise);

		list.add(l.size());

		crit = Restrictions.between("score", 0.25, 0.5);
		l = participationDao.findByScore(crit, entreprise);
		list.add(l.size());
		crit = Restrictions.between("score", 0.5, 0.6);
		l = participationDao.findByScore(crit, entreprise);
		list.add(l.size());
		crit = Restrictions.between("score", 0.6, 0.7);
		l = participationDao.findByScore(crit, entreprise);
		list.add(l.size());
		crit = Restrictions.between("score", 0.7, 0.8);
		l = participationDao.findByScore(crit, entreprise);
		list.add(l.size());
		crit = Restrictions.gt("score", 0.8);
		l = participationDao.findByScore(crit, entreprise);
		list.add(l.size());
		return list;

	}

	public List<Number> findScore(Candidat candidat) {

		List<Number> list = new ArrayList<>();

		Criterion crit = Restrictions.lt("score", 0.25);
		Criterion crit2 = Restrictions.eq("candidat", candidat);
		Criterion crit3 = Restrictions.and(crit, crit2);
		List<Participation> l = participationDao.findByCriteria(crit3);

		list.add(l.size());

		crit = Restrictions.between("score", 0.25, 0.5);
		crit2 = Restrictions.eq("candidat", candidat);
		crit3 = Restrictions.and(crit, crit2);
		l = participationDao.findByCriteria(crit3);
		list.add(l.size());
		crit = Restrictions.between("score", 0.5, 0.6);

		crit2 = Restrictions.eq("candidat", candidat);
		crit3 = Restrictions.and(crit, crit2);
		l = participationDao.findByCriteria(crit3);
		list.add(l.size());
		crit = Restrictions.between("score", 0.6, 0.7);
		crit2 = Restrictions.eq("candidat", candidat);
		crit3 = Restrictions.and(crit, crit2);
		l = participationDao.findByCriteria(crit3);
		list.add(l.size());
		crit = Restrictions.between("score", 0.7, 0.8);
		crit2 = Restrictions.eq("candidat", candidat);
		crit3 = Restrictions.and(crit, crit2);
		l = participationDao.findByCriteria(crit3);
		list.add(l.size());
		crit = Restrictions.gt("score", 0.8);
		crit2 = Restrictions.eq("candidat", candidat);
		crit3 = Restrictions.and(crit, crit2);
		l = participationDao.findByCriteria(crit3);
		list.add(l.size());
		return list;

	}

}
