package com.ant.examen.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ant.examen.dao.ThemeDao;
import com.ant.examen.entities.Theme;

public class Test {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		System.out.println(bCryptPasswordEncoder.encode("123"));
		
//	Theme theme = new Theme();
//	theme.setLibelle("Java");
//	
//	ThemeDao themeDao = new ThemeDao();
//	themeDao.save(theme);

	}

}
