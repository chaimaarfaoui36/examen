package com.ant.examen.utils;

import com.ant.examen.dao.ThemeDao;
import com.ant.examen.entities.Theme;

public class Test {

	public static void main(String[] args) {
		
		
	Theme theme = new Theme();
	theme.setLibelle("Java");
	
	ThemeDao themeDao = new ThemeDao();
	themeDao.save(theme);

	}

}
