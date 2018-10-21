package com.qa.Webservices.ServiceUtilityCalls;

import java.io.File;
import java.util.Map;
import com.qa.Webservices.TestBase.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WebServiceUtility extends TestBase{

	Response httpResponse;
	public Response getRequestCallUsingRESTfulWebService(String URI)
	{
		RequestSpecification reqSpec=RestAssured.given();
			try{
						httpResponse=reqSpec.request(Method.GET,URI);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		return httpResponse;
	}
	
	public Response postRequestCallUsingRESTfulWebService(String URI, Map<String, String> allHeaders, String jsonStringPayload)
	{
		RequestSpecification reqSpec=RestAssured.given();
		reqSpec.headers(allHeaders);
		reqSpec.body(jsonStringPayload);
			try{
						httpResponse=reqSpec.request(Method.POST,URI);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		return httpResponse;
	}
	
	public Response postRequestCallUsingRESTfulWebService(String URI,Map<String,String> allHeaders, File jsonPayloadFile)
	{
		RequestSpecification reqSpec=RestAssured.given();
		reqSpec.headers(allHeaders);
		reqSpec.body(jsonPayloadFile);
		try{
				httpResponse=reqSpec.request(Method.POST,URI);
		}catch(Exception e)
		{
				System.out.println(e);
		}
		return httpResponse;
	}
	
	public Response deleteRequestCallUsingRESTfulWebService(String URI)
	{
		RequestSpecification reqSpec=RestAssured.given();
			try{
					httpResponse=reqSpec.request(Method.DELETE,URI);
			}catch(Exception e)
			{
					System.out.println(e);
			}
		return httpResponse;
	}
}
