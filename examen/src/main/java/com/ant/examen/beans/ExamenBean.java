package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.Reponse;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.ExamenService;

@ManagedBean
@ViewScoped
public class ExamenBean {

	private ExamenService examenService = new ExamenService();
	private EntrepriseService entrepriseService = new EntrepriseService();
	private Examen examen = new Examen();
	private Date minDate = new Date();
	private Date minDateFin = new Date();
	private List<Examen> list = new ArrayList<>();
	private String id;
	private boolean btnAdd, btnEdit;

	@PostConstruct
	public void init() {

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		id = params.get("id");
		if (id == null) {
			examen = new Examen();
			minDate = new Date();
			minDateFin = new Date();
			btnAdd = true;
			btnEdit = false;

		} else {
			Integer idQ = Integer.valueOf(id);
			examen = examenService.findById(idQ);
			minDate = examen.getDateCreation();
			minDateFin = examen.getDateCreation();
			btnAdd = false;
			btnEdit = true;
		}

	}

	public void onDateSelect(SelectEvent event) {
		minDateFin = examen.getDateCreation();
	}

	public String ajouter() {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());
			examen.setEntreprise(entreprise);
			MessageResponse result = examenService.save(examen);

			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				return "ListExamen";
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

			MessageResponse result = examenService.delete(examen);
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

	public String modifier() {

		try {
			
			MessageResponse result = examenService.update(examen);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				return "ListExamen";
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

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public ExamenService getExamenService() {
		return examenService;
	}

	public void setExamenService(ExamenService examenService) {
		this.examenService = examenService;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMinDateFin() {
		return minDateFin;
	}

	public void setMinDateFin(Date minDateFin) {
		this.minDateFin = minDateFin;
	}

	public List<Examen> getList() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());
			list = examenService.findByEntreprise(entreprise);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void setList(List<Examen> list) {
		this.list = list;
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
