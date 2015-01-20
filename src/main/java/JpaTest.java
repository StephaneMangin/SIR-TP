import java.sql.Date;

import javax.persistence.*;


public class JpaTest {
	
	public EntityManager manager;
	
	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
		EntityManager manager = factory.createEntityManager();
		JpaTest jpa = new JpaTest(manager);
		EntityTransaction tx = manager.getTransaction();
		
		tx.begin();
		
		// Personne 1
		Person person1 = new Person();
		person1.setName("Robert");
		person1.setFirstname("Julien");
		person1.setEmail("julien.robert@gmail.com");
		person1.setGender("Masculin");
		person1.setFacebookProfile("519915919845645618918914");
		person1.setBirthday(new Date(10, 02, 1972));
		manager.persist(person1);
		Home home11 = new Home();
		home11.setIpAddress("192.45.185.42");
		home11.setRoomQty(5);
		home11.setPerson(person1);
		home11.setAddress("3Rue de la salamandre 35000 RENNES");
		manager.persist(home11);
		Home home12 = new Home();
		home12.setIpAddress("192.45.185.43");
		home12.setRoomQty(3);
		home12.setPerson(person1);
		home12.setAddress("73 Bvd de la Liberté 35000 RENNES");
		manager.persist(home12);

		// Personne 2
		Person person2 = new Person();
		person2.setName("Marie");
		person2.setFirstname("Julie");
		person2.setEmail("julie.marie@gmail.com");
		person2.setGender("Feminin");
		person2.setFacebookProfile("216659814981894189184195");
		person2.setBirthday(new Date(03, 12, 1950));
		manager.persist(person2);
		Home home21 = new Home();
		home21.setIpAddress("161.24.210.102");
		home21.setRoomQty(3);
		home21.setPerson(person2);
		home21.setAddress("Immeuble Omega 35000 RENNES");
		manager.persist(home21);

		// Personne 3
		Person person3 = new Person();
		person3.setName("Marcel");
		person3.setFirstname("Lucien");
		person3.setEmail("lucien.marcel@gmail.com");
		person3.setGender("Masculin");
		person3.setFacebookProfile("2914848189+48971894864894");
		person3.setBirthday(new Date(12, 06, 1974));
		manager.persist(person3);
		Home home31 = new Home();
		home31.setIpAddress("12.145.85.142");
		home31.setRoomQty(5);
		home31.setPerson(person3);
		home31.setAddress("50 Bvd des Alliés 35000 RENNES");
		manager.persist(home31);
		Home home32 = new Home();
		home32.setIpAddress("19.125.85.60");
		home32.setRoomQty(6);
		home32.setPerson(person3);
		home32.setAddress("1 Place du Palais 35000 RENNES");
		manager.persist(home32);

		// Personne 4
		Person person4 = new Person();
		person4.setName("Beaufort");
		person4.setFirstname("Yvan");
		person4.setEmail("yvan.beaufort@yahoo.fr");
		person4.setGender("Masculin");
		person4.setFacebookProfile("15689498491894156118947565");
		person4.setBirthday(new Date(10, 02, 2015));
		manager.persist(person4);
		Home home41 = new Home();
		home41.setIpAddress("92.145.85.202");
		home41.setRoomQty(8);
		home41.setPerson(person4);
		home41.setAddress("25 Avenue des mouches 35000 RENNES");
		manager.persist(home41);

		// Personne 5
		Person person5 = new Person();
		person5.setName("Martin");
		person5.setFirstname("Delphine");
		person5.setEmail("delphine.martin@orange.fr");
		person5.setGender("Feminin");
		person5.setFacebookProfile("15894894189489469794894946");
		person5.setBirthday(new Date(05, 03, 1990));
		manager.persist(person5);
		Home home51 = new Home();
		home51.setIpAddress("152.125.145.42");
		home51.setRoomQty(2);
		home51.setPerson(person5);
		home51.setAddress("64 Route de Lorient 35000 RENNES");
		manager.persist(home51);
		
		tx.commit();
	}

}
