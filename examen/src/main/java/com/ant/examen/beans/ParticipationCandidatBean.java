package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Participation;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.ParticipationService;

@ManagedBean
@ViewScoped
public class ParticipationCandidatBean {

	private ParticipationService participationService = new ParticipationService();
	private List<Participation> participations = new ArrayList<>();
	private CandidatService candidatService = new CandidatService();
	private ExamenService examenService = new ExamenService();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Candidat candidat = candidatService.findByEmail(authentication.getName());
		participations = participationService.findByCandidat(candidat);

		
	}

	public List<Participation> getParticipations() {

		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

}
