import java.util.Date;

import javax.persistence.*;

@Entity
public class Person {
	
	private long id;
	private String name;
	private String firstname;
	private String gender;
	private String email;
	private String facebookProfile;
    private Date birthday;
    
    public Person() {
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebookProfile() {
		return facebookProfile;
	}
	public void setFacebookProfile(String facebookProfile) {
		this.facebookProfile = facebookProfile;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}