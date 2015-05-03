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

import fr.istic.tpjpa.domain.Heater;

@Path("/heaters")
public class HeaterController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public HeaterController() {
		factory = Persistence.createEntityManagerFactory("example");
		manager = factory.createEntityManager();
		tx = manager.getTransaction();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Heater> getAction() {
		TypedQuery<Heater> q = manager.createQuery(
				"select distinct h from Heater h", Heater.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Heater getAction(@PathParam("id") Long id) {
		TypedQuery<Heater> q = manager.createQuery(
				"select distinct h from Heater h where id=:id", Heater.class)
				.setParameter("id", id);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Heater postAction(Heater heater) {
		tx.begin();
		manager.persist(heater);
		tx.commit();
		return heater;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Heater putAction(Heater heater) {
		tx.begin();
		manager.persist(heater);
		tx.commit();
		return heater;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") Long id) {
		TypedQuery<Heater> q = manager.createQuery(
				"select distinct h from Heater h where id=:id", Heater.class)
				.setParameter("id", id);
		Heater heater = q.getSingleResult();
		tx.begin();
		manager.remove(manager.merge(heater));
		tx.commit();
		return true;
	}
}