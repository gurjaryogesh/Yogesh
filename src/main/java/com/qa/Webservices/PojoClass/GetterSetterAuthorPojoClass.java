package com.qa.Webservices.PojoClass;

public class GetterSetterAuthorPojoClass {

	private String id;
	private TitleObjectClass title;
	private AuthorDataClass author;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TitleObjectClass getTitle() {
		return title;
	}
	public void setTitle(TitleObjectClass title) {
		this.title = title;
	}
	public AuthorDataClass getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDataClass author) {
		this.author = author;
	}
	public GetterSetterAuthorPojoClass(String id, TitleObjectClass title, AuthorDataClass author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public GetterSetterAuthorPojoClass()
	{
		
	}
}
