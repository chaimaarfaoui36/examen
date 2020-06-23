package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Theme;
import com.ant.examen.services.ExamenService;

@ManagedBean
@ViewScoped
public class ExamenDisponibleBean {

	private ExamenService examenService = new ExamenService();
	private List<Examen> examens = new ArrayList<>();
	private List<String> selectThemes;
	private List<String> selectSte;
	@PostConstruct
	public void init() {
		examens = examenService.findDisponibleExamen();
	}

	public void filtrer() {
		
		examens =examenService.findByThemeAndSte(selectThemes, selectSte);
	}
	public List<Examen> getExamens() {
		return examens;
	}

	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}

	public List<String> getSelectThemes() {
		return selectThemes;
	}

	public void setSelectThemes(List<String> selectThemes) {
		this.selectThemes = selectThemes;
	}

	public List<String> getSelectSte() {
		return selectSte;
	}

	public void setSelectSte(List<String> selectSte) {
		this.selectSte = selectSte;
	}
	
	
	
}
