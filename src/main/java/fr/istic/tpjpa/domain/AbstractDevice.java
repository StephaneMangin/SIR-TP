package fr.istic.tpjpa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType= DiscriminatorType.STRING)
public abstract class AbstractDevice implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String type;
	private int wattHeure;

	private List<Home> homes = new ArrayList<Home>();

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(insertable=false, updatable=false)
	public String getType() {
		return type;
	}

	public String setType(String type) {
		return this.type = type;
	}
    public int getWattHeure() {
		return wattHeure;
	}
	public void setWattHeure(int wattHeure) {
		this.wattHeure = wattHeure;
	}

	/**
	 * Retourn la liste des homes
	 *
	 * @return
	 */
	@ManyToMany(mappedBy="devices")
	public List<Home> getHomes() {
		return homes;
	}

	/**
	 * Remplace la liste de devices
	 *
	 * @param homes
	 */
	public void setHomes(List<Home> homes) {
		this.homes.clear();
		this.homes = homes;
	}
	/**
	 * Ajouter un device
	 *
	 * @param home
	 */
	public void addHome(Home home) {
		this.homes.add(home);
		if (!home.getDevices().contains(this)) {
			home.addDevice(this);
		}
	}
	/**
	 * Supprimer un device
	 *
	 * @param home
	 */
	public void delHome(Home home) {
		this.homes.remove(home);
		if (home.getDevices().contains(this)) {
			home.delDevice(this);
		}
	}
}
