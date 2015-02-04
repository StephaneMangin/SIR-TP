package fr.istic.tpjpa.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Home {
	
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
	 * Retourne la propriétaire de la maison
	 * 
	 * @return
	 */
	@ManyToOne
	public Person getPerson() {
		return person;
	}

	/** 
	 * Modifie le propriétaire
	 * 
	 * Process de mise à jour:
	 * Vérifier que ce n'est pas une suppession de propriétaire
	 * Vérifie que le propriétaire actuel n'est pas déjà le même que le nouveau
	 * Faire l'appel récursif sur l'ajout d'une maison au propriétaire
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
	 * Attention appel récursif.
	 * 
	 * Process de mise à jour : 
	 * Pour chaque ancienne maison, désattribuer this uniquement si this est la relation d'origines.
	 * Remettre à jour les nouveaux devices en attribuant this uniquement si aucune maison n'a déjà été attribué.
	 * On remplace effectivement la liste de devices par la nouvelle remise à jour le cas échéant (device déjà attribuée).
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
	 * Attention appel récursif.
	 * On commence par enlever le device de son ancienne maison potentielle.
	 * 
	 * 
	 * @param heatr
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
	 * Attention appel récursif, ne modifier la relation que si this est le propriétaire du device
	 * et que le device a bien comme propriétaire this.
	 * 
	 * @param home
	 */
	public void delDevice(AbstractDevice device) {
		if (device.getHome().equals(this) && this.devices.contains(device)) {
			this.devices.remove(device);
			device.setHome(null);
		}
	}

}
