package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Theme;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.ThemeService;

@ManagedBean
@ViewScoped
public class DetailExamenBean {
	private String id;
	private Examen examen = new Examen();
	private ExamenService examenService = new ExamenService();
	private ThemeService themeService = new ThemeService();
	private List<Theme> themes = new ArrayList<>();
	@PostConstruct
	public void init() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			id = params.get("id");
			if (id != null) {
				Integer idQ = Integer.valueOf(id);
				examen = examenService.findById(idQ);
				themes = themeService.findByExamen(examen);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Examen getExamen() {
		return examen;
	}
	public void setExamen(Examen examen) {
		this.examen = examen;
	}
	public List<Theme> getThemes() {
		return themes;
	}
	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
	
	
}
