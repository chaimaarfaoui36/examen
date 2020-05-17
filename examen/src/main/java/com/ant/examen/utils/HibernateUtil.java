package com.ant.examen.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static HibernateUtil instance;
	private SessionFactory sessionFactory;

	private HibernateUtil() {

		if (sessionFactory == null) {
			Configuration cfg = new Configuration().configure();
			StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
			standardServiceRegistryBuilder.applySettings(cfg.getProperties());
			ServiceRegistry serviceRegistry = standardServiceRegistryBuilder.build();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		}

	}

	public static HibernateUtil getInstance() {

		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
