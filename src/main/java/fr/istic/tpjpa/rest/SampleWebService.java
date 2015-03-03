package fr.istic.tpjpa.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.tpjpa.domain.Heater;
import fr.istic.tpjpa.domain.Home;

@Path("/rest")
public class SampleWebService {
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello, how are you?";
    }

    @GET
    @Path("/home")
    @Produces(MediaType.APPLICATION_JSON)
    public Home getHome() {
        Home h = new Home();
        h.setAddress("Some address");
        Heater h1 = new Heater();
        h1.setWattHeure(500);
        Heater h2 = new Heater();
        h2.setWattHeure(600);
        h.addDevice(h1);
        h.addDevice(h2);
        return h;
    }
}