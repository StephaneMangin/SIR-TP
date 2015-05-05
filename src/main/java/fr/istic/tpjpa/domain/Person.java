package fr.istic.tpjpa.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Person implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String firstname;
	private String gender;
	private String email;
	private String facebookProfile;
    private Date birthday;
    private List<Home> homes = new ArrayList<Home>();
    private List<Person> friends = new ArrayList<Person>();

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
	@JsonManagedReference("person-home")
	@OneToMany(mappedBy="person", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public List<Home> getHomes() {
		return homes;
	}

	/**
	 * Remplace la liste de maison
	 *
	 * @param homes
	 */
	public void setHomes(List<Home> homes) {
		this.homes.clear();
		this.homes = homes;

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
		this.homes.add(home);
		home.setPerson(this);
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
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
	  name="person_friend",
	  joinColumns={@JoinColumn(name="person_id", referencedColumnName="id")},
	  inverseJoinColumns={@JoinColumn(name="friend_id", referencedColumnName="id")})
	public List<Person> getFriends() {
		return friends;
	}

	/**
	 * Remplace la liste d'amis
	 *
	 * @param friends
	 */
	public void setFriends(List<Person> friends) {
		this.friends.clear();
		this.friends = friends;
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
	 * @param friend
	 */
	public void delFriend(Person friend) {
		this.friends.remove(friend);
		if (friend.getFriends().contains(this)) {
			friend.delFriend(this);
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
	@Temporal(TemporalType.DATE)
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
