package fr.istic.tpjpa.domain;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Heater")
public class Heater extends AbstractDevice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
