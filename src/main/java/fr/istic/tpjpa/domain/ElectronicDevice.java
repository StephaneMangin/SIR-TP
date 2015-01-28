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
	 * Modifie le propri�taire
	 * 
	 * Process de mise � jour:
	 * V�rifier que ce n'est pas une suppression de propri�taire
	 * V�rifie que le propri�taire actuel n'est pas d�j� le m�me que le nouveau
	 * Faire l'appel r�cursif sur l'ajout d'un device au propri�taire
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
