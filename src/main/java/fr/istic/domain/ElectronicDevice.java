package fr.istic.domain;
import javax.persistence.*;

@Entity
@DiscriminatorValue("ElectronicDevice")
public class ElectronicDevice extends AbstractDevice {

}
