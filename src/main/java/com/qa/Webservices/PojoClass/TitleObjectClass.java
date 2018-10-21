package com.qa.Webservices.PojoClass;

public class TitleObjectClass {

	private String name;
	private String editor;
	private String copyright_year;
	private AddressDataClass address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getCopyright_year() {
		return copyright_year;
	}
	public void setCopyright_year(String copyright_year) {
		this.copyright_year = copyright_year;
	}
	public AddressDataClass getAddress() {
		return address;
	}
	public void setAddress(AddressDataClass address) {
		this.address = address;
	}
	public TitleObjectClass(String name, String editor, String copyright_year, AddressDataClass address) {
		super();
		this.name = name;
		this.editor = editor;
		this.copyright_year = copyright_year;
		this.address = address;
	}
	
	public TitleObjectClass()
	{
		
	}
}
