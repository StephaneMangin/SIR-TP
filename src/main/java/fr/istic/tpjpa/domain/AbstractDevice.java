package fr.istic.tpjpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
public abstract class AbstractDevice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
