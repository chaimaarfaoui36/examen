package com.ant.examen.services;

import java.util.List;

import com.ant.examen.dao.ParticipationDao;
import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.ReponseCandidat;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.ResultByTheme;
import com.ant.examen.model.ResultatFinal;

public class ResultatService {
	private QuestionService questionService = new QuestionService();
	private ReponseCandidatService reponseCandidatService = new ReponseCandidatService();
	private ParticipationDao participationDao = new ParticipationDao();

	public ResultByTheme calculByTheme(Theme theme, Participation participation) {
		ResultByTheme result = new ResultByTheme();
		List<Question> list = questionService.findByExamenAndTheme(participation.getExamen(), theme);
		result.setTotalReponse(list.size());
		boolean vrai = false;
		int count = 0;
		for (Question quest : list) {
			List<ReponseCandidat> responses = reponseCandidatService.findByQuestion(quest, participation);

			for (ReponseCandidat resp : responses) {
//				if (!quest.isMultiChoice()) {
				if (resp.isEtat() == resp.getReponse().isCorrect()) {
					vrai = true;
				} else {
					vrai = false;
					break;
				}
//				}
			}

			if (vrai) {
				count++;
			}
		}
		result.setReponseCorrect(count);
		return result;

	}

	public ResultatFinal calculByExamen(Participation participation) {
		ResultatFinal result = new ResultatFinal();
		List<Question> list = questionService.findByExamen(participation.getExamen());

		boolean vrai = false;
		int count = 0;
		for (Question quest : list) {
			List<ReponseCandidat> responses = reponseCandidatService.findByQuestion(quest, participation);

			for (ReponseCandidat resp : responses) {
//				if (!quest.isMultiChoice()) {
				if (resp.isEtat() == resp.getReponse().isCorrect()) {
					vrai = true;
				} else {
					vrai = false;
					break;
				}
//				}
			}

			if (vrai) {
				count++;
			}
		}

		double res = (double) count / (double) list.size();

		participation.setScore(res);
		participationDao.update(participation);

		result.setScore(res);
		result.setReponseCorrect(count);
		result.setTotalReponse(list.size());

		if (res < 25) {
			result.setMessage("Très Faible");
		} else if (res >= 25 && res <= 50) {
			result.setMessage("Faible");
		} else if (res > 50 && res <= 60) {
			result.setMessage("Passable");
		} else if (res > 60 && res <= 70) {
			result.setMessage("Bien");
		} else if (res >= 70 && res <= 80) {
			result.setMessage("Très Bien");
		} else {
			result.setMessage("Excelent");
		}

		return result;
	}
}
