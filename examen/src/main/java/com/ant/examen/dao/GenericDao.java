package com.ant.examen.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import com.ant.examen.utils.HibernateUtil;

public class GenericDao<T> {
	private Class<T> clazz;
	protected Session hibernateSession;
	private Transaction tx;

	

	public GenericDao(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	protected void startOperation() {
		hibernateSession = HibernateUtil.getInstance().getSessionFactory().openSession();
		tx = hibernateSession.beginTransaction();
	}

	protected void endOperation() {
		tx.commit();
		hibernateSession.close();
	}

	public void save(T entity) {
		startOperation();
		hibernateSession.save(entity);
		endOperation();
	}

	public void update(T entity) {
		startOperation();
		hibernateSession.update(entity);
		endOperation();
	}

	public void delete(T entity) {
		startOperation();
		hibernateSession.delete(entity);
		endOperation();
	}
	
	
	public List<T> findAll(){
		startOperation();
		List<T> list = hibernateSession.createCriteria(clazz).list();
		hibernateSession.close();
		return list;
	}
	public List<T> findByCriteria(Criterion crit){
		startOperation();
		List<T> list = hibernateSession.createCriteria(clazz).add(crit).list();
		hibernateSession.close();
		return list;
	}
	
}
