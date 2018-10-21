package com.qa.Webservices.TestCases;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Webservices.PojoClass.DataClass;
import com.qa.Webservices.PojoClass.GetterSetterAuthorPojoClass;
import com.qa.Webservices.ServiceUtilityCalls.WebServiceUtility;
import com.qa.Webservices.TestBase.TestBase;
import com.qa.Webservices.TestUtil.TestUtil;
import io.restassured.response.Response;

public class TC01_getRequestResponseRESTfulWebService extends TestBase{

	WebServiceUtility wbservice;
	SoftAssert softassert;
	String BaseURI;
	Response httpResponse;
	ResultSet rs;
	
	@BeforeMethod
	public void SetUpTestCase()
	{
		wbservice=new WebServiceUtility();
		softassert=new SoftAssert();
		BaseURI=prop.getProperty("hostServiceurl")+prop.getProperty("getserviceurl");
	}
	
	@Test
	public void TC01_getRequestResponseRESTfulWebServiceTest() throws JsonParseException, JsonMappingException, IOException
	{
		httpResponse=wbservice.getRequestCallUsingRESTfulWebService(BaseURI);
		
		System.out.println("Response Status Code:"+TestUtil.getStatusCodeFromResponse(httpResponse));
		softassert.assertEquals(TestUtil.getStatusCodeFromResponse(httpResponse), RESPONSE_SUCCESS, "Please contact developer.Reason Code not expected for Get Response");
		if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_SUCCESS)
			{
				//All Headers are captured from the Header Array response using MAP Interface and displayed on Console.
				Map<String,String> allheadersInfo=TestUtil.getAllHeadersInfo(httpResponse);
				for(Map.Entry<String, String> headerEntry:allheadersInfo.entrySet())
					{
						System.out.println("Header Key::"+headerEntry.getKey()+" ||  Header Value:"+headerEntry.getValue());
					}
				
				//Fetching the Response Body and comapring the Data with Request Payload Body
				ObjectMapper mapper=new ObjectMapper();
				GetterSetterAuthorPojoClass getAuthorResponseData=mapper.readValue(httpResponse.asString(), GetterSetterAuthorPojoClass.class);
				softassert.assertEquals(getAuthorResponseData.getId(), "1", "Please check the Request payload and backend system for proper ID validation");
				softassert.assertEquals(getAuthorResponseData.getTitle().getName(), "Harry Potter", "Please check the Request payload and backend system for proper Name validation");
				softassert.assertEquals(getAuthorResponseData.getTitle().getEditor(), "Cambridge Publisher", "Please check the Request payload and backend system for proper Editor validation");
				softassert.assertEquals(getAuthorResponseData.getTitle().getAddress().getCity(), "Cambridge", "Please check the Request payload and backend system for proper City validation");
				softassert.assertEquals(getAuthorResponseData.getTitle().getAddress().getCountry(), "United Kingdom", "Please check the Request payload and backend system for proper Country validation");
				softassert.assertEquals(getAuthorResponseData.getTitle().getCopyright_year(), "2010", "Please check the Request payload and backend system for proper Copyright Year validation");
				List<DataClass> authordetailslist=getAuthorResponseData.getAuthor().getData();
				for(DataClass list:authordetailslist)
					{
						System.out.println("Author's First Name:"+list.getAuthorName().getFirstname());
						System.out.println("Author's Last Name:"+list.getAuthorName().getLastname());
						System.out.println("Author's Birth year:"+list.getAuthorDOY());
					}
			}
		else if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_AUTHENTICATION_ERROR)
			{
				System.out.println("Please verify correct Username and Password/Token being passed in Request.");
			}
		else if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_BAD_REQUEST)
			{
				System.out.println("Please verify correct request Payload is being processed.");
			}
		else if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_PAGE_NOT_FOUND)
			{
				System.out.println("Please verify correct Endpoint is being processed as BaseURI.");
			}
		else if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_SERVER_ERROR)
			{
				System.out.println("Please verify End Server is up and running.");
			}
		softassert.assertAll();
	}

	@Test
	public void TC02_getRequestResponseRESTfulWebServiceTest() throws JsonParseException, JsonMappingException, IOException, SQLException
	{
		httpResponse=wbservice.getRequestCallUsingRESTfulWebService(BaseURI);
		System.out.println("Response Status Code:"+TestUtil.getStatusCodeFromResponse(httpResponse));
		softassert.assertEquals(TestUtil.getStatusCodeFromResponse(httpResponse), RESPONSE_SUCCESS, "Please recheck");
		if(TestUtil.getStatusCodeFromResponse(httpResponse)==RESPONSE_SUCCESS)
			{
				ObjectMapper mapper=new ObjectMapper();
				GetterSetterAuthorPojoClass getAuthorResponse=mapper.readValue(httpResponse.asString(), GetterSetterAuthorPojoClass.class);
				rs=TestUtil.getDatabaseConnectivity("SELECT TABLE1.id AS ID, TABLE2.name AS NAME, TABLE2.editor AS EDITOR, TABLE2.city AS CITY, TABLE2.country AS COUNTRY, TABLE3.authorFname AS FIRST_NAME, TABLE3.authorLname AS LAST_NAME, TABLE3.authorDOY AS AUTHOR_DOY FROM tbl_GetterSetterAuthorPojoClass TABLE1 INNER JOIN tbl_TitleObjectClass TABLE2 ON TABLE1.titleID=TABLE2.titleID INNER JOIN tbl_AuthorObjectClass TABLE3 ON TABLE1.authorID=TABLE3.authorID");
				while(rs.next())
					{
						softassert.assertEquals(getAuthorResponse.getId(), rs.getString("ID"), "Something is not correct.ID is not matching");
						softassert.assertEquals(getAuthorResponse.getTitle().getName(), rs.getString("NAME"), "Something is not correct.Name is not matching");
						softassert.assertEquals(getAuthorResponse.getTitle().getEditor(), rs.getString("EDITOR"), "Something is not correct.Editor is not matching");
						softassert.assertEquals(getAuthorResponse.getTitle().getAddress().getCity(), rs.getString("CITY"), "Something is not correct.City is not matching");
						softassert.assertEquals(getAuthorResponse.getTitle().getAddress().getCountry(), rs.getString("COUNTRY"), "Something is not correct.Country is not matching");
						List<DataClass> authorListArray=getAuthorResponse.getAuthor().getData();
						for(DataClass list:authorListArray)
							{
								if(list.getAuthorDOY().equalsIgnoreCase(rs.getString("AUTHOR_DOY")))
									{
										System.out.println("Actual Data::"+list.getAuthorDOY()+" is Matched");
										break;
									}
							}
						for(DataClass list:authorListArray)
						{
							if(list.getAuthorName().getFirstname().equalsIgnoreCase(rs.getString("FIRST_NAME")))
								{
									System.out.println("Actual Data::"+list.getAuthorName().getFirstname()+" is Matched");
									break;
								}
						}
						for(DataClass list:authorListArray)
						{
							if(list.getAuthorName().getLastname().equalsIgnoreCase(rs.getString("LAST_NAME")))
								{
									System.out.println("Actual Data::"+list.getAuthorName().getLastname()+" is Matched");
									break;
								}
						}
					}
			}
		softassert.assertAll();
	}
}
