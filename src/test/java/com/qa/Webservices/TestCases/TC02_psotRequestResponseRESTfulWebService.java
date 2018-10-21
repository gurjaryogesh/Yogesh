																																																																																																							package com.qa.Webservices.TestCases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Webservices.PojoClass.AddressDataClass;
import com.qa.Webservices.PojoClass.AuthorDataClass;
import com.qa.Webservices.PojoClass.AuthorNameClass;
import com.qa.Webservices.PojoClass.DataClass;
import com.qa.Webservices.PojoClass.GetterSetterAuthorPojoClass;
import com.qa.Webservices.PojoClass.TitleObjectClass;
import com.qa.Webservices.ServiceUtilityCalls.WebServiceUtility;
import com.qa.Webservices.TestBase.TestBase;
import com.qa.Webservices.TestUtil.TestUtil;
import io.restassured.response.Response;

public class TC02_psotRequestResponseRESTfulWebService extends TestBase{

	WebServiceUtility wbservice;
	SoftAssert softassert;
	String BaseURI;
	Response httpResponse;
	
	@BeforeMethod
	public void SetUpTestCase()
	{
		wbservice=new WebServiceUtility();
		softassert=new SoftAssert();
		BaseURI=prop.getProperty("hostServiceurl")+prop.getProperty("postservieurl");
	}
	
	@Test(description="Creating POJO Model Class to create the Payload Request for POST.")
	public void TC01_postRequestResponseRESTfulWebServiceTest() throws IOException
	{
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("username", "arkajnag");
		headers.put("password", "pass1234");
		
		//Jackson API mapper class that helps in Marshelling/Serializing or Deserializing/Unmarshelling.
		//Marshelling/Serializing = POJO to JSONObject
		//Deserializing/Unmarshelling = JSONObject to POJO
		ObjectMapper mapper=new ObjectMapper();
		
		//Using the Model Classes to create the JSON Request
		AuthorNameClass authorname1=new AuthorNameClass("Sujata", "Chakraborty");
		AuthorNameClass authorname2=new AuthorNameClass("Arkajyoti", "Nag");
		DataClass dcls1=new DataClass(authorname1, "1996");
		DataClass dcls2=new DataClass(authorname2, "1986");
		List<DataClass> listAuthorData=new ArrayList<DataClass>();
		listAuthorData.add(dcls1);
		listAuthorData.add(dcls2);
		AuthorDataClass authorData=new AuthorDataClass(listAuthorData);
		AddressDataClass addressData=new AddressDataClass("Kolkata", "India");
		TitleObjectClass titledata=new TitleObjectClass("Suji's Meow", "Nag's Publisher", "2018", addressData);
		GetterSetterAuthorPojoClass authorRequestData=new GetterSetterAuthorPojoClass("2", titledata, authorData);
		String request_Payload=mapper.writeValueAsString(authorRequestData);
		/*
		 * Triggered the POST request with URI, Payload, Headers and HttpVersion status line.
		 * Return is Http Response.
		 */
		httpResponse=wbservice.postRequestCallUsingRESTfulWebService(BaseURI, headers, request_Payload);
		
		
		System.out.println("Response Status Code:"+TestUtil.getStatusCodeFromResponse(httpResponse));
		softassert.assertEquals(TestUtil.getStatusCodeFromResponse(httpResponse), RESPONSE_CREATED, "Please check the Request and review with Developer for further assistance");
		if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_CREATED)
			{
				//Fetching Response Body as String and then unmarshelling the JSONObject to POJO to validate the data.
				GetterSetterAuthorPojoClass authorResponseData=mapper.readValue(httpResponse.asString(), GetterSetterAuthorPojoClass.class);
				softassert.assertEquals(authorResponseData.getId(), authorRequestData.getId(), "Please check with Developer about value of ID");
				softassert.assertEquals(authorResponseData.getTitle().getName(), authorRequestData.getTitle().getName(), "Please check with Developer about value of Name");
				softassert.assertEquals(authorResponseData.getTitle().getEditor(), authorRequestData.getTitle().getEditor(), "Please check with Developer about value of Editor");
				softassert.assertEquals(authorResponseData.getTitle().getCopyright_year(), authorRequestData.getTitle().getCopyright_year(), "Please check with Developer about value of copy-right");
				softassert.assertEquals(authorResponseData.getTitle().getAddress().getCity(), authorRequestData.getTitle().getAddress().getCity(), "Please check with Developer about value of City");
				softassert.assertEquals(authorResponseData.getTitle().getAddress().getCountry(), authorRequestData.getTitle().getAddress().getCountry(), "Please check with Developer about value of Country");
				List<DataClass> responseAuthorList=authorResponseData.getAuthor().getData();
				List<DataClass> requestAuthorList=authorRequestData.getAuthor().getData();
				for(DataClass respData:responseAuthorList)
					{
						for(DataClass reqData:requestAuthorList)
							{
								if(respData.getAuthorDOY().equalsIgnoreCase(reqData.getAuthorDOY()))
									{
										System.out.println("Response and Response for Array Value:"+respData.getAuthorDOY()+" is matched");
										break;
									}
							}
					}
				
				for(DataClass respData:responseAuthorList)
				{
					for(DataClass reqData:requestAuthorList)
						{
							if(respData.getAuthorName().getFirstname().equalsIgnoreCase(reqData.getAuthorName().getFirstname()))
								{
									System.out.println("Response and Response for Array Value:"+respData.getAuthorName().getFirstname()+" is matched");
									break;
								}
						}
				}
				
				for(DataClass respData:responseAuthorList)
				{
					for(DataClass reqData:requestAuthorList)
						{
							if(respData.getAuthorName().getLastname().equalsIgnoreCase(reqData.getAuthorName().getLastname()))
								{
									System.out.println("Response and Response for Array Value:"+respData.getAuthorName().getLastname()+" is matched");
									break;
								}
						}
				}
			}
		else if (TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_AUTHENTICATION_ERROR)
			{
				System.out.println("Please verify correct Username and Password/Token being passed in Request.");
			}
		else if (TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_BAD_REQUEST)
			{
				System.out.println("Please verify correct request Payload is being processed.");
			}
		else if (TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_PAGE_NOT_FOUND)
			{
				System.out.println("Please verify correct Endpoint is being processed as BaseURI.");
			}
		else if (TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_SERVER_ERROR)
			{
				System.out.println("Please verify End Server is up and running.");
			}
		
		softassert.assertAll();
	}

	@Test
	public void TC02_postRequestResponseRESTfulWebServiceTest() throws FileNotFoundException, IOException, ParseException
	{
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type", "application/json");
		headers.put("username", "arkajnag");
		headers.put("password", "pass1234");
		File jsonFile=new File("C:\\Users\\arkaj\\workspace\\RESTfulWebServices\\src\\main\\resources\\BhaagMilkaBhaag.json");
		
		JSONObject jsonRootObject=TestUtil.readJSONFileData(jsonFile);
		System.out.println("Request JSON File:"+jsonRootObject.toString());
		System.out.println("ID in the Request file:"+jsonRootObject.get("id"));
		
		JSONObject jsonRequestTitleData=(JSONObject) jsonRootObject.get("title");
		System.out.println("Request Title Name:"+jsonRequestTitleData.get("name"));
		System.out.println("Request Title Editor:"+jsonRequestTitleData.get("editor"));
		System.out.println("Request Title Copyright Year:"+jsonRequestTitleData.get("copyright_year"));
		
		JSONObject jsonRequestAddressObject=(JSONObject) jsonRequestTitleData.get("address");
		System.out.println("Request Address City:"+jsonRequestAddressObject.get("city"));
		System.out.println("Request Address Country:"+jsonRequestAddressObject.get("country"));
		
		JSONObject jsonRequestAuthorData=(JSONObject) jsonRootObject.get("author");
		JSONArray jsonarrayRequestAuthorData=(JSONArray) jsonRequestAuthorData.get("data");
		for(int i=0;i<=jsonarrayRequestAuthorData.size()-1;i++)
			{
				JSONObject jsonReqAuthData=(JSONObject) jsonarrayRequestAuthorData.get(i);
				System.out.println("Request Author Date of Year Joining:"+jsonReqAuthData.get("authorDOY"));
				JSONObject jsonReqAuthorName=(JSONObject) jsonReqAuthData.get("authorName");
				System.out.println("Request First Name of Author:"+jsonReqAuthorName.get("firstname"));
				System.out.println("Request Last Name of Author:"+jsonReqAuthorName.get("lastname"));
			}
		
		System.out.println("*****************************************************************************************");
		httpResponse=wbservice.postRequestCallUsingRESTfulWebService(BaseURI, headers, jsonFile);
		System.out.println("Response Status Code:"+TestUtil.getStatusCodeFromResponse(httpResponse));
	}
}
