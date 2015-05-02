package fr.istic.tpjpa.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Home implements Serializable {
	
	private long id;
	private String address;
	private int size;
	private String ipAddress;
	private int roomQty;
	private Person person;
	private List<AbstractDevice> devices = new ArrayList<AbstractDevice>();
	
    public Home() {
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
	 * Retourne la propri�taire de la maison
	 * 
	 * @return
	 */
	@ManyToOne
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
	
	/**
	 * Retourn la liste des devices
	 * 
	 * @return
	 */
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="home")
	public List<AbstractDevice> getDevices() {
		return devices;
	}
	
	/**
	 * Remplace la liste de devices
	 * Attention appel r�cursif.
	 * 
	 * Process de mise � jour : 
	 * Pour chaque ancienne maison, d�sattribuer this uniquement si this est la relation d'origines.
	 * Remettre � jour les nouveaux devices en attribuant this uniquement si aucune maison n'a d�j� �t� attribu�.
	 * On remplace effectivement la liste de devices par la nouvelle remise � jour le cas �ch�ant (device d�j� attribu�e).
	 * 
	 * @param devices
	 */
	public void setDevices(List<AbstractDevice> devices) {
		for (AbstractDevice device: this.devices) {
			if (device.getHome().equals(this)) {
				device.setHome(null);
			}
		}
		List<AbstractDevice> actual_devices = new ArrayList<AbstractDevice>();
		for (AbstractDevice device: devices) {
			if (device.getHome() == null) {
				device.setHome(this);
				actual_devices.add(device);
			}
		}
		this.devices = actual_devices;

	}
	/** 
	 * Ajouter un device
	 * 
	 * Attention appel r�cursif.
	 * On commence par enlever le device de son ancienne maison potentielle.
	 * 
	 * 
	 * @param abstractDevice
	 */
	public void addDevice(AbstractDevice abstractDevice) {
		if (abstractDevice.getHome() != null) {
			abstractDevice.getHome().delDevice(abstractDevice);
		}
		this.devices.add(abstractDevice);
		abstractDevice.setHome(this);
	}
	/**
	 * Supprimer un device
	 * 
	 * Attention appel r�cursif, ne modifier la relation que si this est le propri�taire du device
	 * et que le device a bien comme propri�taire this.
	 * 
	 * @param device
	 */
	public void delDevice(AbstractDevice device) {
		if (device.getHome().equals(this) && this.devices.contains(device)) {
			this.devices.remove(device);
			device.setHome(null);
		}
	}
	
}
