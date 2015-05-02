package fr.istic.tpjpa.rest;

import fr.istic.tpjpa.domain.Person;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/persons")
public class PersonController {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private EntityTransaction tx;

	public PersonController() {
			factory = Persistence.createEntityManagerFactory("example");
			manager = factory.createEntityManager();
			tx = manager.getTransaction();
		
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Person> getAction() {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p", Person.class);
		return q.getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getAction(@PathParam("id") Long arg0) {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p where id=:id", Person.class)
				.setParameter("id", arg0);
		return q.getSingleResult();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person postAction(Person person) {
		tx.begin();
		manager.persist(person);
		tx.commit();
		return person;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person putAction(@PathParam("id") String arg0) {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p where id=:id", Person.class)
				.setParameter("id", arg0);
		Person person = q.getSingleResult();
		tx.begin();
		manager.persist(person);
		tx.commit();
		return person;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAction(@PathParam("id") String arg0) {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p where id=:id", Person.class)
				.setParameter("id", arg0);
		Person person = q.getSingleResult();
		tx.begin();
		manager.remove(person);
		tx.commit();
		return true;
	}
}