package com.ant.examen.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Administrateur;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Users;

@ManagedBean
@SessionScoped
public class ConnexionBean implements Serializable {

	private String error;

	public String getProfil() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users user = (Users) authentication.getPrincipal();

		if (user instanceof Administrateur) {
			return "Administrateur";
		} else if (user instanceof Candidat) {
			Candidat candidat = (Candidat) user;
			return candidat.getNom();
		} else if (user instanceof Entreprise) {
			Entreprise entreprise = (Entreprise) user;
			return entreprise.getNomEntreprise();
		}

		return null;
	}

	public String getError() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String id = request.getParameter("error");
		if (id != null) {
			if (Integer.parseInt(id) == 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Attention", "Merci de verifier votre email ou mot de passe"));
			}
		}

		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}