package model;

public class CustomerDetails {
	private String firstname;
	private long id;
	
	public CustomerDetails() {
		super();
	}
	public CustomerDetails(String firstname, long id) {
		super();
		this.firstname = firstname;
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}
