package com.qa.Webservices.TestBase;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {
	
	public static Properties prop;
	public static int RESPONSE_SUCCESS=200;
	public static int RESPONSE_CREATED=201;
	public static int RESPONSE_NO_CONTENT=204;
	public static int RESPONSE_BAD_REQUEST=400;
	public static int RESPONSE_AUTHENTICATION_ERROR=401;
	public static int RESPONSE_PAGE_NOT_FOUND=404;
	public static int RESPONSE_SERVER_ERROR=500;
	
	static
		{
			prop=new Properties();
			try{
					FileInputStream fis=new FileInputStream("C:\\Users\\arkaj\\workspace\\RESTfulWebServices\\src\\main\\java\\com\\qa\\Webservices\\config\\config.properties");
					prop.load(fis);
			}catch(Exception e)
			{
				System.out.println("Exception:"+e);
			}
		}
}
