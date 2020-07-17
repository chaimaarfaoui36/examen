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

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Invitation;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.InvitationService;

@ManagedBean
@ViewScoped
public class InvitationCandidatBean {

	private InvitationService invitationService = new InvitationService();
	private List<Invitation> invitations = new ArrayList<>();
	private List<Invitation> notifications = new ArrayList<>();
	private CandidatService candidatService = new CandidatService();
	private Invitation invitation = new Invitation();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Candidat candidat = candidatService.findByEmail(authentication.getName());
		invitations = invitationService.findByCandidat(candidat);
		notifications = invitationService.findByCandidatAndEtat(candidat, "En attente");
	}

	public void refuser() {
		try {
			invitation.setEtat("Refusée");
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

	}

	public void accepter() {

		try {
			invitation.setEtat("Acceptée");
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

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

	public List<Invitation> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Invitation> notifications) {
		this.notifications = notifications;
	}

}
