package com.ant.examen.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Participation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateParticipation;
	private boolean finished;
	private double score;
	@ManyToOne
	private Candidat candidat;
	@ManyToOne
	private Examen examen;
	@OneToMany(mappedBy = "participation")
	private List<ReponseCandidat> reponseCandidats;
	@OneToMany(mappedBy = "participation")
	private List<Invitation> invitations;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateParticipation() {
		return dateParticipation;
	}

	public void setDateParticipation(Date dateParticipation) {
		this.dateParticipation = dateParticipation;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public List<ReponseCandidat> getReponseCandidats() {
		return reponseCandidats;
	}

	public void setReponseCandidats(List<ReponseCandidat> reponseCandidats) {
		this.reponseCandidats = reponseCandidats;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

}
