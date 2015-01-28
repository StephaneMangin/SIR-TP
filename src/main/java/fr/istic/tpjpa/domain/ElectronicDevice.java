package fr.istic.tpjpa.domain;
import javax.persistence.*;

@Entity
public class ElectronicDevice implements DeviceInterface {
	
	private long id;
	private Home home;
	private int wattHeure;

    public ElectronicDevice() {
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
	
    @ManyToOne
    public Home getHome() {
		return home;
	}
    
    /** 
	 * Modifie le propriétaire
	 * 
	 * Process de mise à jour:
	 * Vérifier que ce n'est pas une suppression de propriétaire
	 * Vérifie que le propriétaire actuel n'est pas déjà le même que le nouveau
	 * Faire l'appel récursif sur l'ajout d'un device au propriétaire
	 * 
	 * @param person
	 */
	public void setHome(Home home) {
		if (home != null && home != this.home) {
			this.home = home;
			home.addDevice(this);
		}
	}
	public int getWattHeure() {
		return wattHeure;
	}
	public void setWattHeure(int wattHeure) {
		this.wattHeure = wattHeure;
	}
}
