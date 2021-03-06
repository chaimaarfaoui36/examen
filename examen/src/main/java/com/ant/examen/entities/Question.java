package com.ant.examen.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String libelle;
	private boolean multiChoice;
	@ManyToOne
	private Theme theme;
	@OneToMany(mappedBy = "question")
	private List<QuestionExamen> questionExamens;
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Reponse> reponses;


	public Question() {
		theme = new Theme();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	

	

	public List<QuestionExamen> getQuestionExamens() {
		return questionExamens;
	}

	public void setQuestionExamens(List<QuestionExamen> questionExamens) {
		this.questionExamens = questionExamens;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public boolean isMultiChoice() {
		return multiChoice;
	}

	public void setMultiChoice(boolean multiChoice) {
		this.multiChoice = multiChoice;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", libelle=" + libelle + ", multiChoice=" + multiChoice + "]";
	}

}
