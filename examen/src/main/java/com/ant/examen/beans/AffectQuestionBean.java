package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.Theme;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.QuestionService;
import com.ant.examen.services.ReponseService;

@ManagedBean
@ViewScoped
public class AffectQuestionBean {

	private String id;
	private Examen examen = new Examen();
	private Theme theme = new Theme();
	private ExamenService examenService = new ExamenService();
	private QuestionService questionService = new QuestionService();
	private List<Question> questions = new ArrayList<>();
	private ReponseService reponseService = new ReponseService();
	private List<Question> selectedQuestions;
	@PostConstruct
	public void init() {

		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			id = params.get("id");
			if (id != null) {
				Integer idQ = Integer.valueOf(id);
				examen = examenService.findById(idQ);
				selectedQuestions = questionService.findByExamen(examen);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeTheme(AjaxBehaviorEvent event) {
		try {
			if (theme.getId() != null) {
				questions = questionService.findByTheme(theme);
				selectedQuestions = questionService.findByExamen(examen);
			} else {
				questions = new ArrayList<>();
				selectedQuestions = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toggleSeelet() {
		try {
			System.out.println(selectedQuestions.isEmpty());
			if(selectedQuestions.isEmpty()) {
				examenService.deletQuestionByTheme(theme, examen);
			}else {
				selectedQuestions.forEach(quest -> {
					examenService.addQuestion(quest, examen);
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onRowSelect(SelectEvent evt) {
		examenService.addQuestion(((Question)evt.getObject()), examen);
	}
	
	public void onRowUnselect(UnselectEvent evt) {
		examenService.removeQuestion(((Question)evt.getObject()), examen);
	}

	public List<Reponse> getReponse(Question question) {
		try {
			return reponseService.findByQuestion(question);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getSelectedQuestions() {
		return selectedQuestions;
	}

	public void setSelectedQuestions(List<Question> selectedQuestions) {
		this.selectedQuestions = selectedQuestions;
	}

}
