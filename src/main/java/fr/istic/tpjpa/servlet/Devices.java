package fr.istic.tpjpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.istic.tpjpa.domain.AbstractDevice;
import fr.istic.tpjpa.domain.ElectronicDevice;
import fr.istic.tpjpa.domain.Heater;
import fr.istic.tpjpa.domain.Person;

@WebServlet(name = "devices", urlPatterns = { "/devices" })
public class Devices extends HttpServlet {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
	private EntityManager manager = factory.createEntityManager();
	private EntityTransaction tx = manager.getTransaction();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Connect to databqse and get all persons informations
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p",
				Person.class);
		List<Person> persons = q.getResultList();
		// Build the response
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		out.println("<HTML>\n<BODY>");
		if (persons.isEmpty()) {
			out.println("<h1>Nothing to show !</h1>");
		} else {
			out.println("<UL>");
			for (Person person : persons) {
				out.println("<LI><H1>"+ person.fullName() +"</H1>");
				out.println("<UL>");
				out.println("<LI>ID: " + person.getId());
				out.println("<LI>Email: " + person.getEmail());
				out.println("<LI>Facebook profile: " + person.getFacebookProfile());
				out.println("<LI>Gender: " + person.getGender());
				out.println("<LI>Birthday: " + person.getBirthday());
				out.println("</UL></LI>");
			}
			out.println("</UL>");
		}
		out.println("</BODY>\n</HTML>");
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		tx.begin();
		AbstractDevice device = null;
		if (request.getParameter("type").equals("Heater")) {
			Heater device1 = new Heater();
		} else if (request.getParameter("type").equals("ElectronicDevice")) {
			ElectronicDevice device1 = new ElectronicDevice();
		}
		device1.setWattHeure(Integer.parseInt(request.getParameter("wattHeure")));
		manager.persist(device);
		tx.commit();

		PrintWriter out = response.getWriter();
		out.println("Inserted");
		out.flush();
	}
}