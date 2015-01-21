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
	public void setHome(Home home) {
		this.home = home;
	}
	public int getWattHeure() {
		return wattHeure;
	}
	public void setWattHeure(int wattHeure) {
		this.wattHeure = wattHeure;
	}
}
