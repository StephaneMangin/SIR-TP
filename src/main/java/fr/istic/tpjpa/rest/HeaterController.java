package fr.istic.tpjpa.rest;

import fr.istic.tpjpa.domain.Heater;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeaterController {

    private List<Heater> heaters = new ArrayList<Heater>();
    private EntityManagerFactory factory = null;
    private EntityManager manager = null;
    private EntityTransaction tx = null;

    public HeaterController() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory("heater_controller");
            manager = factory.createEntityManager();
            tx = manager.getTransaction();
        }
        TypedQuery<Heater> q = manager.createQuery("select distinct h from heater h", Heater.class);
        heaters = q.getResultList();
    }

    @GET @Path("/heaters")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Heater> getAction() {
        return heaters;
    }

    @GET
    @Path("/heater/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Heater getAction(@PathParam("id") String arg0) {
        return heaters.get(Integer.parseInt(arg0));
    }

    @POST @Path("/heater")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Heater postAction(Heater heater) {
        tx.begin();
        manager.persist(heater);
        tx.commit();
        return heater;
    }

    @PUT
    @Path("/heater/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Heater putAction(@PathParam("id") String arg0) {
        Heater heater = heaters.get(Integer.parseInt(arg0));
        tx.begin();
        manager.persist(heater);
        tx.commit();
        return heater;
    }

    @DELETE
    @Path("/home/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteAction(@PathParam("id") String arg0) {
        Heater heater = heaters.remove(Integer.parseInt(arg0));
        tx.begin();
        manager.remove(heater);
        tx.commit();
        return true;
    }
}