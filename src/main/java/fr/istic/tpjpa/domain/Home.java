package fr.istic.tpjpa.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Home implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String address;
	private int size;
	private String ipAddress;
	private int roomQty;
	private Person person;
	private List<AbstractDevice> devices = new ArrayList<AbstractDevice>();

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retourne la propri�taire de la maison
	 *
	 * @return
	 */
	@JsonBackReference("person-home")
	@ManyToOne()
	public Person getPerson() {
		return person;
	}

	/**
	 * Modifie le propri�taire
	 *
	 * Process de mise � jour:
	 * V�rifier que ce n'est pas une suppession de propri�taire
	 * V�rifie que le propri�taire actuel n'est pas d�j� le m�me que le nouveau
	 * Faire l'appel r�cursif sur l'ajout d'une maison au propri�taire
	 *
	 * @param person
	 */
	public void setPerson(Person person) {
		if (person != null && person != this.person) {
			this.person = person;
			person.addHome(this);
		}
	}

	/**
	 * Retourn la liste des devices
	 *
	 * @return
	 */
	@JsonManagedReference("home-device")
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="home_device",
			joinColumns={@JoinColumn(name="home_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="device_id", referencedColumnName="id")})
	public List<AbstractDevice> getDevices() {
		return devices;
	}

	/**
	 * Remplace la liste de devices
	 *
	 * @param devices
	 */
	public void setDevices(List<AbstractDevice> devices) {
		this.devices.clear();
		this.devices = devices;
	}
	/**
	 * Ajouter un device
	 *
	 * @param device
	 */
	public void addDevice(AbstractDevice device) {
		this.devices.add(device);
		if (!device.getHomes().contains(this)) {
			device.addHome(this);
		}
	}
	/**
	 * Supprimer un device
	 *
	 * @param device
	 */
	public void delDevice(AbstractDevice device) {
		this.devices.remove(device);
		if (device.getHomes().contains(this)) {
			device.delHome(this);
		}
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getRoomQty() {
		return roomQty;
	}
	public void setRoomQty(int roomQty) {
		this.roomQty = roomQty;
	}

}
