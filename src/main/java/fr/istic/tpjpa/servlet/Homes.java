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
import fr.istic.tpjpa.domain.Home;

@WebServlet(name = "homes", urlPatterns = { "/homes" })
public class Homes extends HttpServlet {

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
		// Connect to database and get all persons informations
		TypedQuery<Home> q = manager.createQuery(
				"select distinct h from Home h",
				Home.class);
		List<Home> homes = q.getResultList();
		// Build the response
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		out.println("<HTML>\n<BODY>");
        out.println("<form><input type=\"button\" value=\"Back\" onClick=\"history.go(-1);return true;\"></form>");
		if (homes.isEmpty()) {
			out.println("<h1>Nothing to show !</h1>");
		} else {
			out.println("<UL>");
			for (Home home : homes) {
				out.println("<LI><H1>"+ home.getAddress() +"</H1>");
				out.println("<UL>");
				out.println("<LI>ID: " + home.getId());
				out.println("<LI>Size: " + home.getSize());
				out.println("<LI>Room qty: " + home.getRoomQty());
				out.println("<LI>IP address: " + home.getIpAddress());
				out.println("<LI>Devices: ");
				for (AbstractDevice device: home.getDevices()) {
					out.print(device.getName() + ", ");
				}
				out.println("</LI>");
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
		Home home = new Home();
		home.setAddress(request.getParameter("address"));
		home.setIpAddress(request.getParameter("ipAddress"));
		home.setRoomQty(Integer.parseInt(request.getParameter("roomQty")));
		home.setSize(Integer.parseInt(request.getParameter("size")));
		manager.persist(home);
		tx.commit();

		PrintWriter out = response.getWriter();
        out.println("<p>Inserted !</p>" +
                "<form><input type=\"button\" value=\"Back\" onClick=\"history.go(-1);return true;\"></form>");
		out.flush();
	}

}