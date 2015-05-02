package fr.istic.tpjpa.domain;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ElectronicDevice")
public class ElectronicDevice extends AbstractDevice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
