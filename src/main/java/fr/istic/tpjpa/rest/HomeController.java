package fr.istic.tpjpa.rest;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.tpjpa.domain.Home;

@Path("/homes")
public class HomeController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public HomeController() {
			factory = Persistence.createEntityManagerFactory("example");
			manager = factory.createEntityManager();
			tx = manager.getTransaction();
		
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Home> getAction() {
		TypedQuery<Home> q = manager.createQuery(
				"select distinct h from Home h", Home.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Home getAction(@PathParam("id") Long arg0) {
		TypedQuery<Home> q = manager.createQuery(
				"select distinct h from Home h where id=:id", Home.class)
				.setParameter("id", arg0);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Home postAction(Home home) {
		tx.begin();
		manager.persist(home);
		tx.commit();
		return home;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Home putAction(@PathParam("id") String arg0) {
		TypedQuery<Home> q = manager.createQuery(
				"select distinct h from Home h where id=:id", Home.class)
				.setParameter("id", arg0);
		Home home = q.getSingleResult();
		tx.begin();
		manager.persist(home);
		tx.commit();
		return home;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") String arg0) {
		TypedQuery<Home> q = manager.createQuery(
				"select distinct h from Home h where id=:id", Home.class)
				.setParameter("id", arg0);
		Home home = q.getSingleResult();
		tx.begin();
		manager.remove(home);
		tx.commit();
		return true;
	}
}