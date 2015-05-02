package fr.istic.tpjpa.rest;

import fr.istic.tpjpa.domain.Home;

import javax.persistence.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class HomeController {

    private List<Home> homes = new ArrayList<Home>();
    private EntityManagerFactory factory = null;
    private EntityManager manager = null;
    private EntityTransaction tx = null;

    public HomeController() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory("home_controller");
            manager = factory.createEntityManager();
            tx = manager.getTransaction();
        }
        TypedQuery<Home> q = manager.createQuery("select distinct h from Home h", Home.class);
        homes = q.getResultList();
    }

    @GET @Path("/homes")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Home> getAction() {
        return homes;
    }

    @GET
    @Path("/home/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Home getAction(@PathParam("id") String arg0) {
        return homes.get(Integer.parseInt(arg0));
    }

    @POST @Path("/home")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Home postAction(Home home) {
        tx.begin();
        manager.persist(home);
        tx.commit();
        return home;
    }

    @PUT
    @Path("/home/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Home putAction(@PathParam("id") String arg0) {
        Home home = homes.get(Integer.parseInt(arg0));
        tx.begin();
        manager.persist(home);
        tx.commit();
        return home;
    }

    @DELETE
    @Path("/home/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteAction(@PathParam("id") String arg0) {
        Home home = homes.remove(Integer.parseInt(arg0));
        tx.begin();
        manager.remove(home);
        tx.commit();
        return true;
    }
}