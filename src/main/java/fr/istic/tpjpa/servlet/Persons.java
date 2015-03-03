package fr.istic.tpjpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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

import fr.istic.tpjpa.domain.Home;
import fr.istic.tpjpa.domain.Person;

@WebServlet(name = "persons", urlPatterns = { "/persons" })
public class Persons extends HttpServlet {

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
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p  join fetch p.friends",
				Person.class);
		List<Person> persons = q.getResultList();
		// Build the response
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		out.println("<HTML>\n<BODY>");
        out.println("<form><input type=\"button\" value=\"Back\" onClick=\"history.go(-1);return true;\"></form>");
        out.println("<h1>Persons</h1>");
		if (persons.isEmpty()) {
			out.println("<h2>Nothing to show !</h2>");
		} else {
			out.println("<UL>");
			for (Person person : persons) {
				out.println("<LI><H2>"+ person.fullName() +"</H2>");
				out.println("<UL>");
				out.println("<LI>ID: " + person.getId() +"</LI>");
				out.println("<LI>Email: " + person.getEmail() +"</LI>");
				out.println("<LI>Facebook profile: " + person.getFacebookProfile() +"</LI>");
				out.println("<LI>Gender: " + person.getGender() +"</LI>");
				out.println("<LI>Birthday: " + person.getBirthday() +"</LI>");
				out.println("<LI>Homes: ");
				for (Home home: person.getHomes()) {
					out.print(home.getAddress() + ", ");
				}
				out.println("</LI>");
                out.println("<LI>Friends: ");
                for (Person friend: person.getFriends()) {
                    out.print(friend.fullName() + ", ");
                }
                out.println("</LI>");
				out.println("</UL>");
				out.println("</LI>");
			}
			out.println("</UL>");
		}
		out.println("</BODY>\n</HTML>");
		out.flush();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		tx.begin();
		Person person = new Person();
		person.setName(request.getParameter("name"));
		person.setFirstname(request.getParameter("firstname"));
		person.setFacebookProfile(request.getParameter("facebookProfile"));
		person.setGender(request.getParameter("gender"));
		person.setBirthday(
				new Date(
						Integer.parseInt(request.getParameter("birthday")), 
						Integer.parseInt(request.getParameter("birthmonth")), 
						Integer.parseInt(request.getParameter("birthyear"))));
		manager.persist(person);
		tx.commit();

		PrintWriter out = response.getWriter();
        out.println("<p>Inserted !</p>" +
                "<form><input type=\"button\" value=\"Back\" onClick=\"history.go(-1);return true;\"></form>");
		out.flush();
	}
}