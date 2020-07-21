package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.InvitationDao;
import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Invitation;
import com.ant.examen.model.MessageResponse;

public class InvitationService {

	private InvitationDao invitationDao = new InvitationDao();

	public MessageResponse save(Invitation invitation) {

		Criterion crit = Restrictions.eq("participation", invitation.getParticipation());
		List<Invitation> list = invitationDao.findByCriteria(crit);

		if (!list.isEmpty()) {
			return new MessageResponse(false, "Invitation déja envoyée ");
		}
		invitation.setEtat("En attente");
		invitationDao.save(invitation);
		return new MessageResponse(true, "Invitation envoyée avec suucès");
	}

	public List<Invitation> findByEntreprise(Entreprise entreprise) {
		return invitationDao.findByEntreprise(entreprise);
	}

	public MessageResponse update(Invitation invitation) {
		invitationDao.update(invitation);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public List<Invitation> findByCandidat(Candidat candidat) {
		// TODO Auto-generated method stub
		return invitationDao.findByCandidat(candidat);
	}
	
	
	public List<Invitation> findByCandidatAndEtat(Candidat candidat, String etat) {
		// TODO Auto-generated method stub
		return invitationDao.findByCandidatAndEtat(candidat, etat);
	}
	
	public List<Invitation> findByMonth(int month, Entreprise entreprise) {
	return invitationDao.findByMonth(month, entreprise);
	}
	
}
