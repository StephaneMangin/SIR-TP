package fr.istic.domain;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Heater")
public class Heater extends AbstractDevice {
	
}
