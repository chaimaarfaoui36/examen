package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.InvitationDao;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.entities.Invitation;
import com.ant.examen.model.MessageResponse;

public class InvitationService {

	private InvitationDao invitationDao = new InvitationDao();

	public MessageResponse save(Invitation invitation) {

		Criterion crit = Restrictions.eq("participation", invitation.getParticipation());
		List<Invitation> list = invitationDao.findByCriteria(crit);

		if (!list.isEmpty()) {
			return new MessageResponse(false, "Invitation d�ja envoy�e ");
		}
		invitation.setEtat("En attente");
		invitationDao.save(invitation);
		return new MessageResponse(true, "Invitation envoy�e avec suuc�s");
	}

	public List<Invitation> findByEntreprise(Entreprise entreprise) {
		return invitationDao.findByEntreprise(entreprise);
	}
}
