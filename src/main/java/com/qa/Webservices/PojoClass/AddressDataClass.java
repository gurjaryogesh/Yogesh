package com.qa.Webservices.PojoClass;

public class AddressDataClass {

	private String city;
	private String country;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public AddressDataClass(String city, String country) {
		super();
		this.city = city;
		this.country = country;
	}
	
	public AddressDataClass()
	{
		
	}
}
