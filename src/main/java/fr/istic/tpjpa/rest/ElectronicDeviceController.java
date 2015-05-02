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

import fr.istic.tpjpa.domain.ElectronicDevice;

@Path("/electronic_devices")
public class ElectronicDeviceController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public ElectronicDeviceController() {
		factory = Persistence.createEntityManagerFactory("example");
		manager = factory.createEntityManager();
		tx = manager.getTransaction();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ElectronicDevice> getAction() {
		TypedQuery<ElectronicDevice> q = manager.createQuery(
				"select distinct h from ElectronicDevice h", ElectronicDevice.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ElectronicDevice getAction(@PathParam("id") String arg0) {
		TypedQuery<ElectronicDevice> q = manager.createQuery(
				"select distinct h from ElectronicDevice h where id=:id", ElectronicDevice.class)
				.setParameter("id", arg0);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ElectronicDevice postAction(ElectronicDevice electronicDevice) {
		tx.begin();
		manager.persist(electronicDevice);
		tx.commit();
		return electronicDevice;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ElectronicDevice putAction(@PathParam("id") String arg0) {
		TypedQuery<ElectronicDevice> q = manager.createQuery(
				"select distinct h from ElectronicDevice h where id=:id", ElectronicDevice.class)
				.setParameter("id", arg0);
		ElectronicDevice electronicDevice = q.getSingleResult();
		tx.begin();
		manager.persist(electronicDevice);
		tx.commit();
		return electronicDevice;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") String arg0) {
		TypedQuery<ElectronicDevice> q = manager.createQuery(
				"select distinct h from ElectronicDevice h where id=:id", ElectronicDevice.class)
				.setParameter("id", arg0);
		ElectronicDevice electronicDevice = q.getSingleResult();
		tx.begin();
		manager.remove(electronicDevice);
		tx.commit();
		return true;
	}
}