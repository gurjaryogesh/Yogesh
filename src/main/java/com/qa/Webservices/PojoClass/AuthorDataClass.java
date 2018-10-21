package com.qa.Webservices.PojoClass;

import java.util.List;

public class AuthorDataClass {

	private List<DataClass> data=null;

	public List<DataClass> getData() {
		return data;
	}

	public void setData(List<DataClass> data) {
		this.data = data;
	}

	public AuthorDataClass(List<DataClass> data) {
		super();
		this.data = data;
	}
	
	public AuthorDataClass()
	{
		
	}
}
