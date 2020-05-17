package com.ant.examen.services;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ant.examen.dao.ThemeDao;
import com.ant.examen.entities.Theme;
import com.ant.examen.model.MessageResponse;

public class ThemeService {

	private ThemeDao themeDao = new ThemeDao();

	public MessageResponse save(Theme theme) {

		Criterion crit = Restrictions.eq("libelle", theme.getLibelle());
		List<Theme> list = themeDao.findByCriteria(crit);

		if (!list.isEmpty()) {
			return new MessageResponse(false, "Libelle existant!!");
		}

		themeDao.save(theme);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public MessageResponse update(Theme theme) {

		Criterion crit = Restrictions.eq("libelle", theme.getLibelle());
		Criterion crit2 = Restrictions.idEq(theme.getId());
		Criterion crit3 = Restrictions.and(crit, crit2);
		List<Theme> list = themeDao.findByCriteria(crit3);

		if (list.isEmpty()) {
			list = themeDao.findByCriteria(crit);
			if (!list.isEmpty()) {
				return new MessageResponse(false, "Libelle existant!!");
			}
		}

		themeDao.update(theme);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public MessageResponse delete(Theme theme) {

		Criterion crit = Restrictions.isNotEmpty("questions");
		Criterion crit2 = Restrictions.idEq(theme.getId());
		Criterion crit3 = Restrictions.and(crit, crit2);
		List<Theme> list = themeDao.findByCriteria(crit3);

		if (!list.isEmpty()) {
			return new MessageResponse(false, "Thème associé a un ou plusieurs questionst!!");
		}

		themeDao.delete(theme);
		return new MessageResponse(true, "Opération effectuée avec succès");
	}

	public List<Theme> findAll() {
		return themeDao.findAll();
	}
}
