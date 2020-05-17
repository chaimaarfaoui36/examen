package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.ant.examen.entities.Theme;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.ThemeService;

@ManagedBean
@ViewScoped
public class ThemeBean {

	private ThemeService themeService = new ThemeService();
	private List<Theme> list = new ArrayList<Theme>();
	private Theme theme = new Theme();
	private boolean btnAdd, btnEdit;
	
	
	
	public void clickAdd() {
		 theme = new Theme();
		 btnAdd = true;
		 btnEdit = false;
	}
	
	
	public void clickEdit() {

		 btnAdd = false;
		 btnEdit = true;
	}
	public void ajouter() {
		boolean valid = false;
		try {
			MessageResponse result = themeService.save(theme);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				valid = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

		PrimeFaces.current().ajax().addCallbackParam("valid", valid);
	}

	public void modifier() {
		boolean valid = false;
		try {
			MessageResponse result = themeService.update(theme);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				valid = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

		PrimeFaces.current().ajax().addCallbackParam("valid", valid);
	}

	public void supprimer() {
		try {
			MessageResponse result = themeService.delete(theme);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
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

	public List<Theme> getList() {
		try {
			list = themeService.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void setList(List<Theme> list) {
		this.list = list;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}


	public ThemeService getThemeService() {
		return themeService;
	}


	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}


	public boolean isBtnAdd() {
		return btnAdd;
	}


	public void setBtnAdd(boolean btnAdd) {
		this.btnAdd = btnAdd;
	}


	public boolean isBtnEdit() {
		return btnEdit;
	}


	public void setBtnEdit(boolean btnEdit) {
		this.btnEdit = btnEdit;
	}
	
	
	

}
