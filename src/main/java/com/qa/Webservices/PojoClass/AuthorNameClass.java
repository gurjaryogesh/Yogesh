package com.qa.Webservices.PojoClass;

public class AuthorNameClass {
	
	private String firstname;
	private String lastname;
	
	public AuthorNameClass(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public AuthorNameClass()
	{
		
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
