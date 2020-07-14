package com.ant.examen.dao;

import java.util.List;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Invitation;

public class InvitationDao extends GenericDao<Invitation> {

	public InvitationDao() {
		super(Invitation.class);
		// TODO Auto-generated constructor stub
	}

	public List<Invitation> findByEntreprise(Entreprise entreprise) {
		startOperation();
		List<Invitation> list = hibernateSession

				.createQuery("select i from Invitation i " + " where i.participation.examen.entreprise=:entre")
				.setParameter("entre", entreprise).list();

		hibernateSession.close();

		return list;
	}

	
	public List<Invitation> findByCandidat(Candidat candidat) {
		startOperation();
		List<Invitation> list = hibernateSession

				.createQuery("select i from Invitation i " + " where i.participation.candidat=:candidat")
				.setParameter("candidat", candidat).list();

		hibernateSession.close();

		return list;
	}
}