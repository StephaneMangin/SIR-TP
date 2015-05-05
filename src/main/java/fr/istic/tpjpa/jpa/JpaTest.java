package fr.istic.tpjpa.jpa;

import java.sql.Date;
import java.util.*;

import javax.persistence.*;

import fr.istic.tpjpa.domain.*;

public class JpaTest {

    private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * Create devices
	 *
	 */
	public AbstractDevice getDevice(String type, Home home, int num) {
		String name = type + num;
		AbstractDevice device;
		if (type == "heater") {
			try {
				device = manager.createQuery(
						"SELECT a FROM Heater a WHERE a.name = :name",
						Heater.class).setParameter("name", name).getSingleResult();
			} catch (NoResultException a) {
				device = null;
			}
		}else {
			try {
				device = manager.createQuery(
						"SELECT a FROM Heater a WHERE a.name = :name",
						Heater.class).setParameter("name", name).getSingleResult();
			} catch (NoResultException b) {
				device = null;
			}
		}
		if (device == null) {
			Random rand = new Random();
			int wattheure = rand.nextInt(10000 - 2000 + 1) + 2000;
			if (type == "heater") {
				device = new Heater();
			} else {
				device = new ElectronicDevice();
			}
			device.setType(type);
			device.setName(name);
			device.setWattHeure(wattheure);
			manager.persist(device);
		}
		return device;
	}

	/**
	 * 
	 * Create and link homes with persons
	 * Autoassoc devices based on paramaters qties
	 * 
	 * @param person
	 * @param numHome
	 * @param numHeaters
	 * @param numElecDevice
	 */
	public void createHome(Person person, int numHome, int numHeaters, int numElecDevice) {
		Random rand = new Random();
		for (int i = 0; i<numHome; i++) {
			Home home = new Home();
			manager.persist(home);
			// Ip aléatoire
			String IP = rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1;
			home.setIpAddress(IP);
			// TODO: trouver un moyen d'attribuer une adresse al�atoire
			home.setAddress(rand.nextInt(80) + rand.nextInt(6) + " place des Lices 35000 RENNES");
			// Chaque piece est egale � 20 m2
			home.setSize(home.getRoomQty()*20);

			for (int j = 0; j<numHeaters; j++) {
				home.addDevice(getDevice("heater", home, j));
			}
			for (int z = 0; z<numElecDevice; z++) {
				home.addDevice(getDevice("electronicDevice", home, z));
			}
			// 1 chauffage par pieces
			home.setRoomQty(numHeaters);
			// 1 piece = 25m2
			home.setSize(numHeaters * 25);

			home.setPerson(person);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void createPersons(){
		int numOfPersons = manager.createQuery("SELECT a FROM Person a", Person.class).getResultList().size();
		// Flush is done for data insertion mechanism purposes
		if (numOfPersons != 0) {
			manager.createQuery("DELETE FROM AbstractDevice");
			manager.createQuery("DELETE FROM Home");
			manager.createQuery("DELETE FROM Person");
		}
		// Personne 1
		Person person1 = new Person();
		manager.persist(person1);
		person1.setName("Robert");
		person1.setFirstname("Julien");
		person1.setEmail("julien.robert@gmail.com");
		person1.setGender("Masculin");
		person1.setFacebookProfile(person1.getName()+"."+person1.getFirstname());
		person1.setBirthday(new Date(10, 02, 1972));
		createHome(person1, 1, 3, 9);
		
		// Personne 2
		Person person2 = new Person();
		manager.persist(person2);
		person2.setName("Marie");
		person2.setFirstname("Julie");
		person2.setEmail("julie.marie@gmail.com");
		person2.setGender("Feminin");
		person2.setFacebookProfile(person2.getName()+"."+person2.getFirstname());
		person2.setBirthday(new Date(03, 12, 1950));
		createHome(person2, 1, 8, 6);

		// Personne 3
		Person person3 = new Person();
		manager.persist(person3);
		person3.setName("Marcel");
		person3.setFirstname("Lucien");
		person3.setEmail("lucien.marcel@gmail.com");
		person3.setGender("Masculin");
		person3.setFacebookProfile(person3.getName()+"."+person3.getFirstname());
		person3.setBirthday(new Date(12, 06, 1974));
		createHome(person3, 1, 12, 23);

		// Personne 4
		Person person4 = new Person();
		manager.persist(person4);
		person4.setName("Beaufort");
		person4.setFirstname("Yvan");
		person4.setEmail("yvan.beaufort@yahoo.fr");
		person4.setGender("Masculin");
		person4.setFacebookProfile(person4.getName()+"."+person4.getFirstname());
		person4.setBirthday(new Date(10, 02, 2015));
		createHome(person4, 1, 4, 7);

		// Personne 5
		Person person5 = new Person();
		manager.persist(person5);
		person5.setName("Martin");
		person5.setFirstname("Delphine");
		person5.setEmail("delphine.martin@orange.fr");
		person5.setGender("Feminin");
		person5.setFacebookProfile(person5.getName()+"."+person5.getFirstname());
		person5.setBirthday(new Date(05, 03, 1990));
		createHome(person5, 1, 3, 9);
		
		// Attribution des amis
		person1.addFriend(person2);
		person1.addFriend(person3);
		person2.addFriend(person3);
		person2.addFriend(person5);
		person4.addFriend(person1);
		person5.addFriend(person1);
	}

	private void listPersons() {
		List<Person> persons = manager.createQuery("SELECT a FROM Person a", Person.class).getResultList();
		System.out.println("num of persons :" + persons.size());
		for (Person person: persons) {
			System.out.println("Next person : " + person.fullName());
		}
	}

	public void n1Select() {
		TypedQuery<Person> q = manager.createQuery(
				"select p from Person p", Person.class);
		long start = System.currentTimeMillis();
		List<Person> res = q.getResultList();
		for (Person p : res) {
			for (Home h : p.getHomes()) {
				h.getIpAddress();
			}
		}
		long end = System.currentTimeMillis();
		long duree = end - start;
		System.err.println("N1Select : temps d'exec = " + duree);
	}
	
	public void joinFetch() {
		TypedQuery<Person> q = manager.createQuery(
				"select distinct p from Person p join fetch p.homes h",
				Person.class);
		long start = System.currentTimeMillis();
		List<Person> res = q.getResultList();
		for (Person p : res) {
			for (Home h : p.getHomes()) {
				h.getIpAddress();
			}
		}
		long end = System.currentTimeMillis();
		long duree = end - start;
		System.err.println("joinFetch: temps d'exec = " + duree);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("example");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		test.createPersons();
		test.listPersons();
		tx.commit();

		// Requests
		test.joinFetch();
		test.n1Select();
	}

}
