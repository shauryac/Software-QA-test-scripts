import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.server.browserlaunchers.DrivenSeleniumLauncher;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * Created by shauryachawla on 5/20/16.
 */
public class StyleAppTest {
    AppiumDriver driver;
    @Before
    public void setup() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "AndroidTestDevice");

        driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }
    /* disable the driver after test has been executed */
    /*the method runs when the test is over*/
    @After
    public void teardown()throws Exception{
        driver.quit();
    }

    @Test
     public void Testing(){
        //LOGIN functionality

        Boolean login= false;
        //an array data structure to hold pass
        String[] user_pass=new String[2];
        user_pass[0]="wrongPass";
        user_pass[1]="test1234";

        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        // clicking cross button
        driver.findElementByClassName("android.view.View").click();
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        //clicking LOG IN
        driver.findElementByName("LOG IN").click();

        //Login with facebook functionality
        driver.findElement(By.name("Log in with Facebook")).click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

        //entering Email or Phone in the textbox field
        driver.findElement(By.name("Email or Phone")).sendKeys("team287SJSU@gmail.com");
        driver.hideKeyboard();

        // Entering Facebook password
        //using loop condition and if else control structures to check for both valid and invalid password

        for(int i=0; i<user_pass.length; i++){

            driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).sendKeys(user_pass[i]);
            driver.hideKeyboard();
            //
            driver.findElement(By.xpath("//android.widget.Button[@index='3']")).click();

            if(i==0){
                login=driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).isDisplayed();

                if(login) {
                    System.out.println("lOGIN with wrong password is working !");
                }
                else{
                    System.out.println("login with wrong password isn't working !");
                }
            }
            else {
                if(driver.findElement(By.xpath("//android.view.View[@index='0']")).getTagName().equals("android.view.View")) {

                    System.out.println("login with correct password working !");
                }
                else{
                    System.out.println("login with correct password isn't working !");
                }
            }
        }
    }

    @Test
    //search functionality
    public void searchFunc(){

        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        //cross functionality
        driver.findElementByName("x").click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        //clicking search bar
        driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        //entering BARBER/barber in search bar
        driver.findElement(By.xpath("//android.widget.EditText[@index='1']")).sendKeys("BARBER");
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);

        //clicking Men's Haircut from the options of the search
        driver.findElementByName("Men's Haircut").click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        //check if options are displayed and search works
        Boolean search= driver.findElement(By.xpath("//android.view.View[@index='6']")).isDisplayed();
        if(search){
            System.out.println("Search working!");
        }
        else{
            System.out.println("Search not working!");
        }
    }

}




