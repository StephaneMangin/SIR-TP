package fr.istic.tpjpa.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Person implements Serializable {
	
	private long id;
	private String name;
	private String firstname;
	private String gender;
	private String email;
	private String facebookProfile;
    private Date birthday;
    private List<Home> homes = new ArrayList<Home>();
    private List<Person> friends = new ArrayList<Person>();
    
    public Person() {
		super();
	}
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Retourne la liste de maison
	 * 
	 * @return
	 */
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="person")
	public List<Home> getHomes() {
		return homes;
	}
	/**
	 * Remplace la liste de maison
	 * Attention appel r�cursif.
	 * 
	 * Process de mise � jour : 
	 * Pour chaque ancienne maison, d�sattribuer this uniquement si this est la relation d'origines.
	 * Remettre � jour les nouvelles maison en attribuant this uniquement si aucune personne n'a d�j� �t� attribu�.
	 * On remplace effectivement la liste de maison par la nouvelle remise � jour le cas �ch�ant (maison d�j� attribu�e).
	 * 
	 * @param homes
	 */
	public void setHomes(List<Home> homes) {
		for (Home home: this.homes) {
			if (home.getPerson().equals(this)) {
				home.setPerson(null);
			}
		}
		List<Home> actual_homes = new ArrayList<Home>();
		for (Home home: homes) {
			if (home.getPerson() == null) {
				home.setPerson(this);
				actual_homes.add(home);
			}
		}
		this.homes = actual_homes;

	}
	/** 
	 * Ajouter une maison
	 * 
	 * Attention appel r�cursif, ne pas s'attribuer la maison si d�j� attribuer � quelqu'un.
	 * 
	 * 
	 * @param home
	 */
	public void addHome(Home home) {
		if (home.getPerson() != null && home.getPerson().equals(this)) {
			this.homes.add(home);
			home.setPerson(this);
		}
	}
	/**
	 * Supprimer une maison
	 * 
	 * Attention appel r�cursif, ne modifier la relation si this est le propri�taire de la maison
	 * et que la maison a bien comme propri�taire this.
	 * 
	 * @param home
	 */
	public void delHome(Home home) {
		if (home.getPerson().equals(this) && this.homes.contains(home)) {
			this.homes.remove(home);
			home.setPerson(null);
		}
	}
	/**
	 * Retourne la liste d'amis
	 * 
	 * @return
	 */
	@ManyToMany
/*	  @JoinTable(
	      name="person_friends",
	      joinColumns={@JoinColumn(name="person_id", referencedColumnName="id")},
	      inverseJoinColumns={@JoinColumn(name="friend_id", referencedColumnName="id")})*/
	public List<Person> getFriends() {
		return friends;
	}
	
	/**
	 * Remplace la liste d'amis et met � jour la liste de chaque ami
	 * Attention car appel r�cursif, l'invariante d'arr�t est la pr�sence
	 * de this dans la liste de l'ami.
	 * 
	 * @param friends
	 */
	public void setFriends(List<Person> friends) {
		List<Person> actual_friends = new ArrayList<Person>();
		// On commence par supprimer this dans chaque ancien ami.
		for (Person friend: friends) {
			if (!friend.getFriends().contains(this)) {
				actual_friends.add(friend);
			}
		}
		// On attribue la nouvelle liste d'amis
		this.friends = actual_friends;
		// On recr�e ensuite la relation inverse
		for (Person friend: this.friends) {
			friend.addFriend(this);
		}
	}
	/**
	 * Ajoute un ami et ajout this dans la liste de l'ami
	 * Attention appel r�cursif, l'invariante d'arr�t est que this n'est pas d�j� dans la liste du nouvel ami
	 * 
	 * @param friend
	 */
	public void addFriend(Person friend) {
		this.friends.add(friend);
		if (!friend.getFriends().contains(this)) {
			friend.addFriend(this);
		}
	}
	/**
	 * Supprime un ami
	 * Attention car r�cursif, l'invariante d'arr�t est la pr�sence de this dans la liste d'ami de old_friend
	 * 
	 * @param old_friend
	 */
	public void delFriend(Person old_friend) {
		this.friends.remove(old_friend);
		if (old_friend.getFriends().contains(this)) {
			old_friend.delFriend(this);
		}
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebookProfile() {
		return facebookProfile;
	}
	public void setFacebookProfile(String facebookProfile) {
		this.facebookProfile = facebookProfile;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String fullName() {
		return this.name + " " + this.firstname;
	}

}
