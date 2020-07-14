package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Invitation;
import com.ant.examen.entities.Participation;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.InvitationService;
import com.ant.examen.services.ParticipationService;

@ManagedBean
@ViewScoped
public class ParticipationEntrepriseBean {
	private ExamenService examenService = new ExamenService();
	private List<Examen> examens = new ArrayList<>();
	private ParticipationService participationService = new ParticipationService();
	private EntrepriseService entrepriseService = new EntrepriseService();
	private List<Participation> participations = new ArrayList<>();
	private InvitationService invitationService = new InvitationService();
	private Invitation invitation = new Invitation();
	private Participation participation;

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());
		examens = examenService.findByEntreprise(entreprise);

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("id");
		if (id != null) {
			Integer idQ = Integer.valueOf(id);
			Examen examen = examenService.findById(idQ);
			participations = participationService.findByExamen(examen);
		}

	}

	public void loadParticipation(Participation part) {
		participation = part;
	}

	public void inviter() {
		invitation.setParticipation(participation);

		boolean valid = false;
		try {
			MessageResponse result = invitationService.save(invitation);
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}
		PrimeFaces.current().ajax().addCallbackParam("valid", valid);
	}

	public int caluclParticipation(Examen exam) {
		return participationService.findByExamen(exam).size();
	}

	public List<Examen> getExamens() {
		return examens;
	}

	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

}
