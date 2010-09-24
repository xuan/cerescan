package com.loquatic.cerescan.api.persistence.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loquatic.cerescan.api.entities.User;

@Service("userManager")
public class UserManager implements UserDetailsService {
	private Log log = LogFactory.getLog(UserManager.class);
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("cerescan-mysql");

	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		EntityManager em = emf.createEntityManager();
		List<User> users = em.createNamedQuery("User.findByUsername")
				.setParameter("username", username).getResultList();
		if (users == null || users.isEmpty()) {
			log.warn("user: " + username + " not found");
			throw new UsernameNotFoundException("user '" + username
					+ "' not found...");
		} else {
			UserDetails user = users.get(0);
			log.debug("logging in user: " + user.getUsername());
			return (UserDetails) user;
		}
	}
}
