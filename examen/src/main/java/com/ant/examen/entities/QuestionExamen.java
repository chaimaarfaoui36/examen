package com.ant.examen.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_examen")
public class QuestionExamen {
	@EmbeddedId
	private QuestionExamenId id;
	@ManyToOne
	@JoinColumn(name = "questions_id", insertable = false, updatable = false)
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "examens_id", insertable = false, updatable = false)
	private Examen examen;

	public QuestionExamenId getId() {
		return id;
	}

	public void setId(QuestionExamenId id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

}
