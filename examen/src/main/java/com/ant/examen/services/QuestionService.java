package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.QuestionDao;
import com.ant.examen.dao.ReponseDao;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.MessageResponse;

public class QuestionService {

	private QuestionDao questionDao = new QuestionDao();
	private ReponseDao reponseDao = new ReponseDao();

	public MessageResponse save(Question question) {

		questionDao.save(question);
		for (Reponse rep : question.getReponses()) {
			rep.setQuestion(question);
			reponseDao.save(rep);
		}
		return new MessageResponse(true, "Opération effectuée avec succès");

	}

	public MessageResponse update(Question question) {
		Criterion crit1 = Restrictions.idEq(question.getId());
		Criterion crit2 = Restrictions.isNotEmpty("examens");

		Criterion crit = Restrictions.and(crit1, crit2);
		List<Question> list = questionDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return new MessageResponse(false, "Question effectée a un ou plusieurs examens");
		}
		questionDao.update(question);
		reponseDao.deleteByQuestion(question);

		for (Reponse rep : question.getReponses()) {
			rep.setQuestion(question);
			reponseDao.save(rep);
		}
		return new MessageResponse(true, "Opération effectuée avec succès");

	}

	public MessageResponse delete(Question question) {
		Criterion crit1 = Restrictions.idEq(question.getId());
		Criterion crit2 = Restrictions.isNotEmpty("examens");

		Criterion crit = Restrictions.and(crit1, crit2);
		List<Question> list = questionDao.findByCriteria(crit);
		if (!list.isEmpty()) {
			return new MessageResponse(false, "Question effectée a un ou plusieurs examens");
		}
		questionDao.delete(question);

		return new MessageResponse(true, "Opération effectuée avec succès");

	}

	public List<Question> findByTheme(Theme theme) {

		return questionDao.findByCriteria(Restrictions.eq("theme", theme));
	}

	public Question findById(Integer id) {
		List<Question> list = questionDao.findByCriteria(Restrictions.idEq(id));
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
