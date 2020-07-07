package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.ReponseCandidat;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.ResultByTheme;
import com.ant.examen.model.ResultatFinal;
import com.ant.examen.services.ParticipationService;
import com.ant.examen.services.QuestionService;
import com.ant.examen.services.ReponseCandidatService;
import com.ant.examen.services.ResultatService;
import com.ant.examen.services.ThemeService;

@ManagedBean
@ViewScoped
public class ResultatBean {
	private String id;
	private Participation participation = new Participation();
	private ReponseCandidatService reponseCandidatService = new ReponseCandidatService();
	private List<ReponseCandidat> reponseCandidats = new ArrayList<>();
	private QuestionService questionService = new QuestionService();
	private ThemeService themeService = new ThemeService();
	private List<Theme> themes = new ArrayList<>();
	private Examen examen = new Examen();
	private ResultByTheme resultByTheme = new ResultByTheme();
	private ParticipationService participationService = new ParticipationService();
	private ResultatService resultService = new ResultatService();
	private ResultatFinal result = new ResultatFinal();
	@PostConstruct
	public void init() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			id = params.get("id");
			if (id != null) {
				Integer idQ = Integer.valueOf(id);

				participation = participationService.findById(idQ);
				examen = participation.getExamen();
				themes = themeService.findByExamen(examen);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calculByTheme(Theme theme) {
		resultByTheme = resultService.calculByTheme(theme, participation);
	}

	public void  calculByParticipation() {
		result =   resultService.calculByExamen(participation);
	}

	public List<Question> findQuestionByTheme(Theme theme) {
		return questionService.findByExamenAndTheme(examen, theme);
	}

	public List<ReponseCandidat> findByQuestion(Question question) {
		List<ReponseCandidat> result = reponseCandidatService.findByQuestion(question, participation);

		return result;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public ResultByTheme getResultByTheme() {
		return resultByTheme;
	}

	public void setResultByTheme(ResultByTheme resultByTheme) {
		this.resultByTheme = resultByTheme;
	}

	public ResultatFinal getResult() {
		return result;
	}

	public void setResult(ResultatFinal result) {
		this.result = result;
	}

}
