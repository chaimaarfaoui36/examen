package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Invitation;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.InvitationService;

@ManagedBean
@ViewScoped
public class InvitationEntrepriseBean {

	private InvitationService invitationService = new InvitationService();
	private List<Invitation> invitations = new ArrayList<>();
	private EntrepriseService entrepriseService = new EntrepriseService();
	private Invitation invitation = new Invitation();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());
		invitations = invitationService.findByEntreprise(entreprise);
	}

	public void refuser() {
		try {
			invitation.setEtat("Annul�e");
			MessageResponse result = invitationService.update(invitation);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Op�ration non effectu�e"));
			e.printStackTrace();
		}

	}

	public void inviter() {

		boolean valid = false;
		try {
			MessageResponse result = invitationService.update(invitation);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
				valid = true;
				invitation = new Invitation();
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Op�ration non effectu�e"));
			e.printStackTrace();
		}
		PrimeFaces.current().ajax().addCallbackParam("valid", valid);
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

}
