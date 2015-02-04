package fr.istic.tpjpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.istic.tpjpa.domain.AbstractDevice;
import fr.istic.tpjpa.domain.ElectronicDevice;
import fr.istic.tpjpa.domain.Heater;

@WebServlet(name = "devices", urlPatterns = { "/devices" })
public class Devices extends HttpServlet {

	private EntityManagerFactory factory = null;
	private EntityManager manager = null;
	private EntityTransaction tx = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("example");
			manager = factory.createEntityManager();
			tx = manager.getTransaction();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Build the response
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		out.println("<HTML>\n<BODY>");
		// Connect to database and get all persons informations
		TypedQuery<String> types = manager.createQuery(
				"select distinct a.type from AbstractDevice a",
				String.class);
		for (String type: types.getResultList()) {
			TypedQuery<AbstractDevice> q = manager.createQuery(
					"select distinct a from AbstractDevice a where a.type=" + type,
					AbstractDevice.class);

			out.println("<h1>"+ type +"</h1>");
			out.println("<UL>");
			List<AbstractDevice> devices = q.getResultList();
			if (devices.isEmpty()) {
				out.println("<h2>Nothing to show !</h2>");
			} else {
				out.println("<UL>");
				for (AbstractDevice device : devices) {
					out.println("<LI><H2>"+ device.getName() +"</H2>");
					out.println("<UL>");
					out.println("<LI>ID: " + device.getId() + "</LI>");
					out.println("<LI>Power: " + device.getWattHeure() +" WH</LI>");
					out.println("</UL></LI>");
				}
				out.println("</UL>");
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
			device = new Heater();
		} else if (request.getParameter("type").equals("ElectronicDevice")) {
			device = new ElectronicDevice();
		}
		device.setWattHeure(Integer.parseInt(request.getParameter("wattHeure")));
		manager.persist(device);
		tx.commit();

		PrintWriter out = response.getWriter();
		out.println("Inserted");
		out.flush();
	}
}