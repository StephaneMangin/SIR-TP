package fr.istic.tpjpa.domain;
import javax.persistence.*;

@Entity
@DiscriminatorValue("ElectronicDevice")
public class ElectronicDevice extends AbstractDevice {

}
