package com.ant.examen.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ExamenDao;
import com.ant.examen.dao.QuestionExamenDao;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.QuestionExamen;
import com.ant.examen.entities.QuestionExamenId;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.MessageResponse;

public class ExamenService {

	private ExamenDao examenDao = new ExamenDao();
	private QuestionExamenDao questionExamenDao = new QuestionExamenDao();

	public MessageResponse save(Examen examen) {

		examenDao.save(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public MessageResponse delete(Examen examen) {

		Criterion crit1 = Restrictions.idEq(examen.getId());
		Criterion crit2 = Restrictions.isNotEmpty("participations");
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Examen> examens = examenDao.findByCriteria(crit3);
		if (!examens.isEmpty()) {
			return new MessageResponse(false, "Existe un ou plusieurs participtation des candidat");
		}
		examenDao.delete(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public MessageResponse update(Examen examen) {

		Criterion crit1 = Restrictions.idEq(examen.getId());
		Criterion crit2 = Restrictions.isNotEmpty("participations");
		Criterion crit3 = Restrictions.and(crit1, crit2);
		List<Examen> examens = examenDao.findByCriteria(crit3);
		if (!examens.isEmpty()) {
			return new MessageResponse(false, "Existe un ou plusieurs participtation des candidat");
		}
		examenDao.update(examen);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public List<Examen> findByEntreprise(Entreprise entreprise) {
		Criterion crit = Restrictions.eq("entreprise", entreprise);
		return examenDao.findByCriteria(crit);
	}

	public Examen findById(Integer idQ) {
		Criterion crit = Restrictions.idEq(idQ);
		List<Examen> examens = examenDao.findByCriteria(crit);
		if (!examens.isEmpty()) {
			return examens.get(0);
		}
		return null;
	}

	public void addQuestion(Question question, Examen examen) {

		Criterion crit = Restrictions.eq("question", question);
		Criterion crit2 = Restrictions.eq("examen", examen);
		Criterion crit3 = Restrictions.and(crit, crit2);
		List<QuestionExamen> list = questionExamenDao.findByCriteria(crit3);
	
		if (list.isEmpty()) {
			QuestionExamen questionExamen = new QuestionExamen();
			QuestionExamenId questionExamenId = new QuestionExamenId();
			
			questionExamenId.setExamensId(examen.getId());
			questionExamenId.setQuestionsId(question.getId());
			questionExamen.setId(questionExamenId);
			questionExamenDao.save(questionExamen);
		}

	}

	public void removeQuestion(Question question, Examen examen) {

		QuestionExamen questionExamen = new QuestionExamen();
		QuestionExamenId questionExamenId = new QuestionExamenId();
		questionExamenId.setExamensId(examen.getId());
		questionExamenId.setQuestionsId(question.getId());
		questionExamen.setId(questionExamenId);
		questionExamenDao.delete(questionExamen);
	}

	public void deletQuestionByTheme(Theme theme, Examen examen) {

		List<QuestionExamen> list = questionExamenDao.findByThemeExamen(theme, examen);
		list.forEach(qe -> {
			questionExamenDao.delete(qe);
		});
	}

	public List<Examen> findDisponibleExamen() {

		Criterion crit = Restrictions.ge("dateExpiration", new Date());
		return examenDao.findByCriteria(crit);
	}

	public List<Examen> findByThemeAndSte(List<String> themes, List<String> stes) {
		List<Integer> newListTheme = themes.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

		List<Integer> newListSte = stes.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		if (stes.isEmpty() && !themes.isEmpty()) {
			return examenDao.findByTheme(newListTheme);
		} else if (!stes.isEmpty() && themes.isEmpty()) {
			return examenDao.findBySte(newListSte);
		}  else if (!stes.isEmpty() && !themes.isEmpty()) {
			return examenDao.findByThemeAndSte(newListTheme, newListSte);
		} else {
			return findDisponibleExamen();
		}
	}

}
