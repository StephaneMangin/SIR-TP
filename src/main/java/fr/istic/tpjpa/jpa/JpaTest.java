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
	 * 
	 * Create and link homes with persons
	 * Autogenerates devices based on paramaters qties
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
			// Ip al�atoire
			String IP = rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1 + "." + rand.nextInt(254) + 1;
			home.setIpAddress(IP);
			// TODO: trouver un moyen d'attribuer une adresse al�atoire
			home.setAddress(i + " place des Lices 35000 RENNES");
			// Chaque piece est egale � 20 m2
			home.setSize(home.getRoomQty()*20);
			
			for (int j = 0; j<numHeaters; j++) {
				int wattheure = rand.nextInt(10000 - 2000 + 1) + 2000;
				Heater heater = new Heater();
				heater.setName("Heater " + j);
				heater.setWattHeure(wattheure);
				home.addDevice(heater);
				manager.persist(heater);
			}
			for (int z = 0; z<numElecDevice; z++) {
				int wattheure = rand.nextInt(2500 - 200 + 1) + 200;
				ElectronicDevice device = new ElectronicDevice();
				device.setName("Electronic device " + z);
				device.setWattHeure(wattheure);
				home.addDevice(device);
				manager.persist(device);
			}
			
			// 1 chauffage par pieces
			home.setRoomQty(numHeaters);
			home.setPerson(person);
			manager.persist(home);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void createPersons(){
		int numOfPersons = manager.createQuery("SELECT a FROM Person a", Person.class).getResultList().size();
		// Flush is done for data insertion mechanism purposes
		if (numOfPersons != 0) {
			manager.createQuery("DROP TABLE ElectronicDevice");
			manager.createQuery("DROP TABLE Heater");
			manager.createQuery("DROP TABLE Home");
			manager.createQuery("DROP TABLE person_friends");
			manager.createQuery("DROP TABLE Person");
		}
		// Personne 1
		Person person1 = new Person();
		person1.setName("Robert");
		person1.setFirstname("Julien");
		person1.setEmail("julien.robert@gmail.com");
		person1.setGender("Masculin");
		person1.setFacebookProfile(person1.getName()+"."+person1.getFirstname());
		person1.setBirthday(new Date(10, 02, 1972));
		manager.persist(person1);
		createHome(person1, 1, 3, 9);
		
		// Personne 2
		Person person2 = new Person();
		person2.setName("Marie");
		person2.setFirstname("Julie");
		person2.setEmail("julie.marie@gmail.com");
		person2.setGender("Feminin");
		person2.setFacebookProfile(person2.getName()+"."+person2.getFirstname());
		person2.setBirthday(new Date(03, 12, 1950));
		manager.persist(person2);
		createHome(person2, 1, 8, 6);

		// Personne 3
		Person person3 = new Person();
		person3.setName("Marcel");
		person3.setFirstname("Lucien");
		person3.setEmail("lucien.marcel@gmail.com");
		person3.setGender("Masculin");
		person3.setFacebookProfile(person3.getName()+"."+person3.getFirstname());
		person3.setBirthday(new Date(12, 06, 1974));
		manager.persist(person3);
		createHome(person3, 1, 12, 23);

		// Personne 4
		Person person4 = new Person();
		person4.setName("Beaufort");
		person4.setFirstname("Yvan");
		person4.setEmail("yvan.beaufort@yahoo.fr");
		person4.setGender("Masculin");
		person4.setFacebookProfile(person4.getName()+"."+person4.getFirstname());
		person4.setBirthday(new Date(10, 02, 2015));
		manager.persist(person4);
		createHome(person4, 1, 4, 7);

		// Personne 5
		Person person5 = new Person();
		person5.setName("Martin");
		person5.setFirstname("Delphine");
		person5.setEmail("delphine.martin@orange.fr");
		person5.setGender("Feminin");
		person5.setFacebookProfile(person5.getName()+"."+person5.getFirstname());
		person5.setBirthday(new Date(05, 03, 1990));
		manager.persist(person5);
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
