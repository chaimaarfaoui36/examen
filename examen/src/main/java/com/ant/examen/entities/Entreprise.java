package com.ant.examen.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Entreprise extends Users {
	
	@OneToMany(mappedBy = "entreprise")
	private List<Examen> examens;

	public List<Examen> getExamens() {
		return examens;
	}

	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}




	
}
