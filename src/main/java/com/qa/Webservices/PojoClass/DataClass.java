package com.qa.Webservices.PojoClass;

public class DataClass {

	private AuthorNameClass authorName;
	private String authorDOY;
	
	public AuthorNameClass getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(AuthorNameClass authorName) {
		this.authorName = authorName;
	}
	
	public String getAuthorDOY() {
		return authorDOY;
	}
	
	public void setAuthorDOY(String authorDOY) {
		this.authorDOY = authorDOY;
	}
	public DataClass(AuthorNameClass authorName, String authorDOY) {
		super();
		this.authorName = authorName;
		this.authorDOY = authorDOY;
	}
	
	public DataClass()
	{
		
	}
}
