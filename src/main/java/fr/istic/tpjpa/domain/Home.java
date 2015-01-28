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
	 * Attention appel récursif.
	 * 
	 * Process de mise à jour : 
	 * Pour chaque ancienne maison, désattribuer this uniquement si this est la relation d'origines.
	 * Remettre à jour les nouveauc chauffages en attribuant this uniquement si aucune maison n'a déjà été attribué.
	 * On remplace effectivement la liste de chauffage par la nouvelle remise à jour le cas échéant (chauffage déjà attribuée).
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
	 * Attention appel récursif.
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
	 * Attention appel récursif, ne modifier la relation que si this est le propriétaire du chauffage
	 * et que le chauffage a bien comme propriétaire this.
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
	 * Attention appel récursif.
	 * 
	 * Process de mise à jour : 
	 * Pour chaque ancienne maison, désattribuer this uniquement si this est la relation d'origines.
	 * Remettre à jour les nouveaux devices en attribuant this uniquement si aucune maison n'a déjà été attribué.
	 * On remplace effectivement la liste de devices par la nouvelle remise à jour le cas échéant (device déjà attribuée).
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
	 * Attention appel récursif.
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
	 * Attention appel récursif, ne modifier la relation que si this est le propriétaire du device
	 * et que le device a bien comme propriétaire this.
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
