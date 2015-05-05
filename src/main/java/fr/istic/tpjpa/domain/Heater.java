package fr.istic.tpjpa.domain;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Heater")
public class Heater extends AbstractDevice {
	
}
