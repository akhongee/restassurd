package restassured;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetTest {
	
	@Test
		public void GetWDetails()
		{   
			//https://jsonplaceholder.typicode.com/
			// Specify the base URL to the RESTful web service
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

			// Get the RequestSpecification of the request that you want to sent
			// to the server. The server is specified by the BaseURI that we have
			// specified in the above step.
			RequestSpecification httpRequest = RestAssured.given();

			// Make a request to the server by specifying the method Type and the method URL.
			// This will return the Response from the server. Store the response in a variable.
			Response response = httpRequest.request(Method.GET, "/todos/1");

			// Now let us print the body of the message to see what response
			// we have recieved from the server
			String responseBody = response.getBody().asString();
			System.out.println("Response Body is =>  " + responseBody);

		}
		
		@Test
		public void GetHeaders()
		{
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, "/todos/1");
		 
			// Reader header of a give name. In this line we will get
			// Header named Content-Type
			String contentType = response.header("Content-Type");
			System.out.println("Content-Type value: " + contentType);
		 
			// Reader header of a give name. In this line we will get
			// Header named Server
			String serverType =  response.header("Server");
			System.out.println("Server value: " + serverType);
		 
			// Reader header of a give name. In this line we will get
			// Header named Content-Encoding
			String acceptLanguage = response.header("Content-Encoding");
			System.out.println("Content-Encoding: " + acceptLanguage);

		
		
		}

		
		@Test
		public void IteratingOverHeaders()
		{
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, "/todos/1");
		 
			// Get all the headers. Return value is of type Headers.
			// Headers class implements Iterable interface, hence we
			// can apply an advance for loop to go through all Headers
			// as shown in the code below
			Headers allHeaders = response.headers();
		 
			// Iterate over all the Headers
			for(Header header : allHeaders)
			{
				System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
			}
		}
		@Test
		public void VerifyCityInJsonResponse()
		{
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, "/todos/1");	 
			// First get the JsonPath object instance from the Response interface
			JsonPath jsonPathEvaluator = response.jsonPath();
		 
			// Then simply query the JsonPath object to get a String value of the node
			// specified by JsonPath: City (Note: You should not put $. in the Java code)
			String city = jsonPathEvaluator.get("title");
		 
			// Let us print the city variable to see what we got
			System.out.println("Title received from Response " + city);
		 
			// Validate the response
			Assert.assertEquals(city, "delectus aut autem", "Correct title  received in the Response");
		 
		}
		@Test
		public void brokenLinkTest() throws MalformedURLException {
		  	System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");

			  WebDriver driver = new ChromeDriver();
			  
			  driver.get("http://restservicestesting.blogspot.in/");
			
			List<String> hrefs = new ArrayList<String>();
			  List<WebElement> anchors = driver.findElements(By.tagName("a"));

			  for (WebElement anchor : anchors) {

			    if ( anchor.getAttribute("href") != null ) 
			    hrefs.add(anchor.getAttribute("href"));

			  }
			  for (String href : hrefs) {

			      int responseCode = returnStatusCode(new URL(href));
			      if ( responseCode != 200 ) {
			        System.out.println("The broken Link is "+"("+responseCode+")" + href);
			      }
			      else {
			        System.out.println("The working Link is " +"("+responseCode+")" + href);

			      }
			    }

			}
	public int returnStatusCode(URL url) {
			Response resp = RestAssured.given().when().get(url);
			int respCode = resp.getStatusCode();
			System.out.println(respCode);
			return respCode;
		}

}
