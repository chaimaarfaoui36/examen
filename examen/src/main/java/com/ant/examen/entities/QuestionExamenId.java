package com.ant.examen.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuestionExamenId implements Serializable{

	@Column(name = "questions_id")
	private Integer questionsId;
	@Column(name = "examens_id")
	private Integer examensId;
	public Integer getQuestionsId() {
		return questionsId;
	}
	public void setQuestionsId(Integer questionsId) {
		this.questionsId = questionsId;
	}
	public Integer getExamensId() {
		return examensId;
	}
	public void setExamensId(Integer examensId) {
		this.examensId = examensId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((examensId == null) ? 0 : examensId.hashCode());
		result = prime * result + ((questionsId == null) ? 0 : questionsId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionExamenId other = (QuestionExamenId) obj;
		if (examensId == null) {
			if (other.examensId != null)
				return false;
		} else if (!examensId.equals(other.examensId))
			return false;
		if (questionsId == null) {
			if (other.questionsId != null)
				return false;
		} else if (!questionsId.equals(other.questionsId))
			return false;
		return true;
	}
	
	
	
}
