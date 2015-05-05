package fr.istic.rest;

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

import fr.istic.domain.AbstractDevice;

@Path("/devices")
public class DeviceController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public DeviceController() {
		factory = Persistence.createEntityManagerFactory("example");
		manager = factory.createEntityManager();
		tx = manager.getTransaction();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AbstractDevice> getAction() {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct h from AbstractDevice h", AbstractDevice.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AbstractDevice getAction(@PathParam("id") String arg0) {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct h from AbstractDevice h where id=:id", AbstractDevice.class)
				.setParameter("id", arg0);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AbstractDevice postAction(AbstractDevice heater) {
		tx.begin();
		manager.persist(heater);
		tx.commit();
		return heater;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AbstractDevice putAction(@PathParam("id") String arg0) {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct h from AbstractDevice h where id=:id", AbstractDevice.class)
				.setParameter("id", arg0);
		AbstractDevice heater = q.getSingleResult();
		tx.begin();
		manager.persist(heater);
		tx.commit();
		return heater;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") String arg0) {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct h from AbstractDevice h where id=:id", AbstractDevice.class)
				.setParameter("id", arg0);
		AbstractDevice heater = q.getSingleResult();
		tx.begin();
		manager.remove(heater);
		tx.commit();
		return true;
	}
}