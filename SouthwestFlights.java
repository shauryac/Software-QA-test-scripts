package AutomationTestSouthwestAirlines;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class SouthwestFlights {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.southwest.com/flight/");
		
		ArrayList<String> al = new ArrayList<>();
		al.add("Summary of the Automation test cases");
		al.add("************");
		al.add(" ");
		// Checking the title of webpage
		al.add("test case#1: Checking the title of webpage");
		al.add("Webpage Title: " + driver.getTitle());
			//	+ " with Search Fares, Air Tickets & Find Travel Deals | Southwest Airlines");
		if (driver.getTitle().equals("Search Fares, Air Tickets & Find Travel Deals | Southwest Airlines")) {
			al.add("Title matches for url!");
		} else {
			al.add("oops,title is changed for url from the time, the test was written!");
		}
		
		// variables to hold the depart and return date fields/web elements on webpage
		WebElement startdatelabel = driver.findElement(By.xpath(".//*[@id='outboundDate']"));
		WebElement enddatelabel = driver.findElement(By.xpath(".//*[@id='returnDate']"));

		al.add(" ");
		al.add("************");
		al.add("test case#2: Checking for round trip radio button functionality");
		// checking for round trip radio button functionality 
		// both departure and return date should be available on web-page for round-trip
		driver.findElement(By.xpath(".//*[@id='roundTrip']")).click();
		Thread.sleep(5000);
		if (startdatelabel.isEnabled() && enddatelabel.isEnabled()) {
			al.add("Round trip works!");
		} else {
			al.add("Round trip does not work!");
		}
		al.add(" ");
		al.add("************");
		al.add("test case#3: Checking for one-way trip radio button functionality");
		// checking one-way trip radio button functionality that departure date should be available/visible
		// but not return date field
		driver.findElement(By.xpath(".//*[@id='oneWay']")).click();
		Thread.sleep(5000);
		if (startdatelabel.isEnabled() && !(enddatelabel.isEnabled())) {
			al.add("One Way Trip works!");
		} else {
			al.add("One Way Trip functionality doesn't work !");
		}
		
		al.add(" ");
		al.add("************");
		al.add("test case#4: Checking if images exist on webpage");

		// checking if the Southwest airline image exist in the page
		if (driver.findElements(By.cssSelector("div[id='hero-items']")).size() != 0) {
			al.add("South West airlines image Exists!");
		} else {
			al.add("Sorry image does not exist!");
		}
		al.add(" ");
		al.add("************");
		al.add("test case#5: Checking for broken links on webpage");

		// First finding the total no of links in page and prints it in console.
		List<WebElement> total_links = driver.findElements(By.tagName("a"));
		al.add("Total Number of links found in page = " + total_links.size());
		// for loop to open all links one by one to check response code.
		boolean isValid = false;
		
		int failed = 0;
		// for loop to open all links one by one to check response code
		for (int i = 0; i < total_links.size(); i++) {
			String url = total_links.get(i).getAttribute("href");

			if (url != null) {

				// getResponseCode function for each URL to check response code
				
				isValid = getResponseCode(url);

				if (!isValid) {
					failed++;
			    } 
			}else {
				// If <a> tag do not contain href attribute and value then print
				al.add("tag does not contain href attribute");
				continue;
			    }
			}

		al.add("No. of Broken Links ----> " + failed);
		
		al.add(" ");
		al.add("************");
		al.add("test case#6: Checking for static dropdown--> Passengers dropdpwn and if the options are available");
		
		// Static drop down box to select number of passengers in Passengers-adults drop box field
		Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='adultPassengerCount']")));
		
		//checking if the user can see options available in passenger drop down
		
		List<WebElement> options = dropdown.getOptions();
		// Printing the options in the adults passenger drop-down field 
		for (WebElement option : options) {
			al.add(option.getText()); 
													
		}
		//Now check if all options can be visible or available on webpage 
		if (options.size() == 9) {
			al.add("All 9 options from 0 Adults to 8 Adults are available");
		} else {
			al.add("All 9 options from 0 Adults to 8 Adults are not available");
		}

		
		al.add(" ");
		al.add("************");
		al.add("test case#7:Checking Login form functionality");

		// using traversexpath.
		//if username is provided and password is not, does it display error message
		
		driver.findElement(By.xpath(".//form[@id='loyaltyLoginForm']/div[1]/input")).sendKeys("test123");
		//Login button
		driver.findElement(By.xpath(".//form[@id='loyaltyLoginForm']/div[3]/span/button")).click();
		// Verifying if error messages is displayed for authentication failure by clicking login button
		String InvalidLoginMessage = driver
				.findElement(By.xpath("//div[@id='global_account_bar']/div[1]/div/div/div[3]/ul/li[1]")).getText();
		al.add("Login without display should display Please enter your Password., but displayed "
				+ InvalidLoginMessage);
		if (InvalidLoginMessage.equals("Please enter your Password.")) {
			al.add("Correct message is displayed");
		} else {
			al.add("Incorrect message is displayed");
		}
		Thread.sleep(5000);
		
		al.add(" ");
		al.add("************");
		al.add("test case#8: ");
		// check if flight details are available
		driver.findElement(By.id("originAirport_displayed")).clear();
		driver.findElement(By.id("originAirport_displayed")).sendKeys("SFO");
		driver.findElement(By.id("destinationAirport_displayed")).clear();
		driver.findElement(By.id("destinationAirport_displayed")).sendKeys("MSP");
		driver.findElement(By.xpath(".//button[@id='submitButton']")).click();
		if (driver.findElements(By.xpath(".//*[@id='errors']")).size() == 0) {
			al.add("Flight Details for the requested fields are available");
		} else {
			al.add("There are no Flights for the fields mentioned");
		}
		
		//printing everything
		for(int i =0; i<al.size(); i++){
			System.out.println(al.get(i));
		}
			
	}

	// Function for getresponse() of link URL.
	// Link URL Is valid If found response code = 200.
	// Link URL Is Invalid If found response code = 404 or 505.
	public static boolean getResponseCode(String hyperlinkUrl) {
		boolean validResponse = false;
		try {
			// Get response code of URL
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet urlresp = new HttpGet(hyperlinkUrl);
			HttpResponse request = client.execute(urlresp);
			int resp_Code = request.getStatusLine().getStatusCode();
			if ((resp_Code == 404) || (resp_Code == 505)) {
				validResponse = false;
				System.out.println("Response Code Is : " + resp_Code + "for url" + hyperlinkUrl);
			} else {
				validResponse = true;
			}
		} catch (Exception e) {

		}
		return validResponse;
	}
}


