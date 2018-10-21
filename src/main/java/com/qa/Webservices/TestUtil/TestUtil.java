package com.qa.Webservices.TestUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qa.Webservices.TestBase.TestBase;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TestUtil extends TestBase{

	public static Map<String, String> getAllHeadersInfo(Response httpResponse)
	{
		Headers allheaders=httpResponse.headers();
		Map<String,String> headermap=new HashMap<String,String>();
		for(Header head:allheaders)
			{
				headermap.put(head.getName(), head.getValue());
			}
		return headermap;
	}
	
	public static int getStatusCodeFromResponse(Response httpResponse)
	{
		return httpResponse.getStatusCode();
	}
	
	public static Connection con;
	public static Statement stmt;
	public static ResultSet rs;	
	public static ResultSet getDatabaseConnectivity(String Query)
	{
		String connectionUrl=prop.getProperty("jdbcConnectionURL");
		Properties p=new Properties();
		p.put("username", "sa");
		p.put("password", "pass1234");
		try {
					Class.forName(prop.getProperty("jdbcClassName"));
					con=DriverManager.getConnection(connectionUrl, p);
					stmt=con.createStatement();
					rs=stmt.executeQuery(Query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static JSONObject readJSONFileData(File jsonFile) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser=new JSONParser();
		JSONObject jsonObject=(JSONObject)parser.parse(new FileReader(jsonFile));
		return jsonObject;
	}
}
