package fr.istic.tpjpa.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AbstractDevice")
public abstract class AbstractDevice {
	
	private long id;
	private String name;
	private String type;
	private Home home;
	private int wattHeure;

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
