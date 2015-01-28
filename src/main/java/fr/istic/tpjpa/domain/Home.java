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
	private List<Heater> heatrs = new ArrayList<Heater>();
	private List<ElectronicDevice> devices = new ArrayList<ElectronicDevice>();
	
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
	 * Retourn la liste des chauffages
	 * 
	 * @return
	 */
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="home")
	public List<Heater> getHeatrs() {
		return heatrs;
	}
	
	/**
	 * Remplace la liste de chauffage
	 * Attention appel r�cursif.
	 * 
	 * Process de mise � jour : 
	 * Pour chaque ancienne maison, d�sattribuer this uniquement si this est la relation d'origines.
	 * Remettre � jour les nouveauc chauffages en attribuant this uniquement si aucune maison n'a d�j� �t� attribu�.
	 * On remplace effectivement la liste de chauffage par la nouvelle remise � jour le cas �ch�ant (chauffage d�j� attribu�e).
	 * 
	 * @param heatrs
	 */
	public void setHeatrs(List<Heater> heatrs) {
		for (Heater heatr: this.heatrs) {
			if (heatr.getHome().equals(this)) {
				heatr.setHome(null);
			}
		}
		List<Heater> actual_heatsr = new ArrayList<Heater>();
		for (Heater heatr: heatrs) {
			if (heatr.getHome() == null) {
				heatr.setHome(this);
				actual_heatsr.add(heatr);
			}
		}
		this.heatrs = actual_heatsr;

	}
	/** 
	 * Ajouter un chauffage
	 * 
	 * Attention appel r�cursif.
	 * On commence par enlever le heater de son ancienne maison potentielle.
	 * 
	 * 
	 * @param heatr
	 */
	public void addHeatr(Heater heatr) {
		if (heatr.getHome() != null) {
			heatr.getHome().delHeater(heatr);
		}
		this.heatrs.add(heatr);
		heatr.setHome(this);
	}
	/**
	 * Supprimer un chauffage
	 * 
	 * Attention appel r�cursif, ne modifier la relation que si this est le propri�taire du chauffage
	 * et que le chauffage a bien comme propri�taire this.
	 * 
	 * @param home
	 */
	public void delHeater(Heater heatr) {
		if (heatr.getHome().equals(this) && this.heatrs.contains(heatr)) {
			this.heatrs.remove(heatr);
			heatr.setHome(null);
		}
	}
	
	/**
	 * Retourn la liste des devices
	 * 
	 * @return
	 */
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="home")
	public List<ElectronicDevice> getDevices() {
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
	public void setDevices(List<ElectronicDevice> devices) {
		for (ElectronicDevice device: this.devices) {
			if (device.getHome().equals(this)) {
				device.setHome(null);
			}
		}
		List<ElectronicDevice> actual_devices = new ArrayList<ElectronicDevice>();
		for (ElectronicDevice device: devices) {
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
	 * @param heatr
	 */
	public void addDevice(ElectronicDevice device) {
		if (device.getHome() != null) {
			device.getHome().delDevice(device);
		}
		this.devices.add(device);
		device.setHome(this);
	}
	/**
	 * Supprimer un device
	 * 
	 * Attention appel r�cursif, ne modifier la relation que si this est le propri�taire du device
	 * et que le device a bien comme propri�taire this.
	 * 
	 * @param home
	 */
	public void delDevice(ElectronicDevice device) {
		if (device.getHome().equals(this) && this.heatrs.contains(device)) {
			this.devices.remove(device);
			device.setHome(null);
		}
	}

}
