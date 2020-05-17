package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.QuestionService;
import com.ant.examen.services.ReponseService;
import com.ant.examen.services.ThemeService;

@ManagedBean
@ViewScoped
public class QuestionBean {

	private QuestionService questionService = new QuestionService();
	private ReponseService reponseService = new ReponseService();
	private Question question = new Question();
	private Theme theme = new Theme();
	private List<Reponse> reponses = new ArrayList<>();
	private ThemeService themeService = new ThemeService();
	private List<Question> questions = new ArrayList<>();
	private String id;

	@PostConstruct
	public void init() {

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		id = params.get("id");
		if (id == null) {
			question = new Question();
			reponses = new ArrayList<>();
			reponses.add(new Reponse());
			reponses.add(new Reponse());

		} else {
			Integer idQ = Integer.valueOf(id);
			question = questionService.findById(idQ);
			reponses = reponseService.findByQuestion(question);
		}

	}

	public void addLine() {
		reponses.add(new Reponse());
	}

	public void removeLine(int index) {
		if (reponses.size() > 2) {
			reponses.remove(index);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention",
					"Nombre des réponses ne doit pas inférieur de 2"));
		}

	}

	public void changeTheme(AjaxBehaviorEvent event) {
		try {
			if (theme.getId() != null) {
				questions = questionService.findByTheme(theme);
			} else {
				questions = new ArrayList<>();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkMulti() {
		reponses.stream().map(s -> {
			s.setCorrect(false);
			return s;
		}).collect(Collectors.toList());
	}

	public void change(int index, Reponse reponse) {
		reponses.set(index, reponse);
	}

	public void checkReponse(int index, Reponse reponse) {

		if (!question.isMultiChoice()) {

			List<Reponse> list = reponses.stream().filter(r -> r.isCorrect() == true).collect(Collectors.toList());
			if (list.size() > 1) {

				reponses.stream().map(s -> {
					s.setCorrect(false);
					return s;
				}).collect(Collectors.toList());
			}
			reponse.setCorrect(true);
		}
		System.out.println(reponse);
	//	
		reponses.set(index, reponse);

	}

	public String valider() {
		question.setReponses(reponses);
		try {

			MessageResponse result;
			if (id == null) {

				result = questionService.save(question);
			} else {

				result = questionService.update(question);
				return "ListQuestion";
			}
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				init();

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}
		return null;

	}

	public void supprimer() {

		try {

			MessageResponse result = questionService.delete(question);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				init();
				questions = questionService.findByTheme(theme);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
