package com.ant.examen.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date dateInvitation;
	private String etat;
	@ManyToOne
	private Participation participation;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDateInvitation() {
		return dateInvitation;
	}
	public void setDateInvitation(Date dateInvitation) {
		this.dateInvitation = dateInvitation;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Participation getParticipation() {
		return participation;
	}
	public void setParticipation(Participation participation) {
		this.participation = participation;
	}
	
	
	
}
