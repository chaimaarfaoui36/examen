package com.ant.examen.dao;

import java.util.List;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Examen;
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

	public List<Invitation> findByCandidatAndEtat(Candidat candidat, String etat) {
		startOperation();
		List<Invitation> list = hibernateSession

				.createQuery(
						"select i from Invitation i " + " where i.participation.candidat=:candidat and i.etat=:etat")
				.setParameter("candidat", candidat).setParameter("etat", etat).list();

		hibernateSession.close();

		return list;
	}

	public List<Invitation> findByMonth(int month, Entreprise entreprise) {

		startOperation();

		List<Invitation> list = hibernateSession
				.createQuery("select e from Invitation e  "
						+ "   where e.participation.examen.entreprise=:entreprise and Month(e.dateInvitation)=:month "
						+ " and YEAR(e.dateInvitation) = YEAR(CURRENT_DATE)")
				.setParameter("month", month).setParameter("entreprise", entreprise).list();

		hibernateSession.close();

		return list;
	}
}
