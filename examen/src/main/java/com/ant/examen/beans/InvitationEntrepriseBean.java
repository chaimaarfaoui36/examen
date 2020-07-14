package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Invitation;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.InvitationService;

@ManagedBean
@ViewScoped
public class InvitationEntrepriseBean {

	private InvitationService invitationService = new InvitationService();
	private List<Invitation> invitations = new ArrayList<>();
	private EntrepriseService entrepriseService = new  EntrepriseService();
	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());
		invitations = invitationService.findByEntreprise(entreprise);
	}
	
	
	public List<Invitation> getInvitations() {
		return invitations;
	}
	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	
	
}
