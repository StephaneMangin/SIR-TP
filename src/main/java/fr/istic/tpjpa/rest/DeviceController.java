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

import com.sun.jersey.spi.resource.Singleton;
import fr.istic.tpjpa.domain.AbstractDevice;

@Singleton
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
	public AbstractDevice getAction(@PathParam("id") Long id) {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct h from AbstractDevice h where id=:id", AbstractDevice.class)
				.setParameter("id", id);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AbstractDevice postAction(AbstractDevice abstractDevice) {
		tx.begin();
		manager.persist(abstractDevice);
		tx.commit();
		return abstractDevice;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AbstractDevice putAction(AbstractDevice abstractDevice) {
		tx.begin();
		manager.merge(abstractDevice);
		tx.commit();
		return abstractDevice;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") Long id) {
		TypedQuery<AbstractDevice> q = manager.createQuery(
				"select distinct ad from AbstractDevice ad where id=:id", AbstractDevice.class)
				.setParameter("id", id);
		AbstractDevice abstractDevice = q.getSingleResult();
		tx.begin();
		manager.remove(manager.merge(abstractDevice));
		tx.commit();
		return true;
	}
}