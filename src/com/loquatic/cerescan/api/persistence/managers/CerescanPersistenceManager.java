package com.loquatic.cerescan.api.persistence.managers;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.loquatic.cerescan.api.entities.Patient;

public class CerescanPersistenceManager {

	protected static EntityManagerFactory emFactory;

	protected CerescanPersistenceManager() {
	}

	public static EntityManager getEntityManager() {
		if (emFactory == null) {
			emFactory = Persistence
					.createEntityManagerFactory("cerescan-mysql");
		}
		return emFactory.createEntityManager();
	}

	public static void persist(Object o) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
	}

	public static void merge(Object o) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
	}

	public static void delete(Object o) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.remove(o);
		em.getTransaction().commit();
	}

	protected static List genericOneParamFinder(String queryName,
			String paramName, String paramValue) {
		Query query = getEntityManager().createNamedQuery(queryName);
		query.setParameter(paramName, paramValue);

		List listOfObjects = query.getResultList();

		return listOfObjects;
	}

	protected static Object genericOneParamSingleResultFinder(String queryName,
			String paramName, String paramValue) {
		Query query = getEntityManager().createNamedQuery(queryName);
		query.setParameter(paramName, paramValue);

		Object obj = query.getSingleResult();

		return obj;
	}

	protected static List genericMultiParamMultiResultFinder(String queryName,
			Map<String, String> params) {
		Query query = getEntityManager().createNamedQuery(queryName);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}

	protected static List genericNoParamFinder(String queryName) {
		Query query = getEntityManager().createNamedQuery(queryName);

		List listOfObjects = query.getResultList();

		return listOfObjects;

	}

	protected static List genericFindAll(String queryName) {
		Query query = getEntityManager().createNamedQuery(queryName);

		List listOfObjects = query.getResultList();

		return listOfObjects;
	}

	protected static Object findById(String queryName, long id) {
		EntityManager em = CerescanPersistenceManager.getEntityManager();
		Object foundObj = em.createNamedQuery(queryName).setParameter("id", id)
				.getSingleResult();
		return foundObj;
	}
}
