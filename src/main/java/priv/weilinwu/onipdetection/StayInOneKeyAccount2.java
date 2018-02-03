package priv.weilinwu.onipdetection;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StayInOneKeyAccount2 extends Thread{
	public static final Logger logger = LoggerFactory.getLogger(PnpAcceptionChangeDetection.class);
	public  WebDriver driver;
	public final String userName = "weilinwu";
	public final String passwd = "7773257Mon777!";
	public final String webAddress = "https://www.one-key.gov.on.ca/iaalogin/IAALogin.jsp";
	
	public StayInOneKeyAccount2() {
		System.setProperty("webdriver.chrome.driver", "D:\\Desktop\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();

	    //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public void run() {
		launchWebsite();
		login();
		while(true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.navigate().refresh();			
		}
	}
	
	public void launchWebsite() {
		driver.navigate().to(webAddress);
		int count = 0;
		while(true) {
			try {
				driver.findElement(By.id("Login"));
			} catch (NoSuchElementException e) {
				logger.info("element not found~");
				count++;
				if(count == 10) {
					count = 0;
					driver.navigate().refresh();
					logger.info("Web page refreshed!~~");
				}
				continue;
			}
			
			// if the element above is found, it means the web page is successfully opened
			logger.info("ONe-key web page is opened!~");
			break;
		}
	}
	
	public void login() {
		driver.findElement(By.id("ldap_user")).sendKeys(userName);
		driver.findElement(By.id("ldap_password")).sendKeys(passwd);
		driver.findElement(By.id("Login")).click();
	}
	
}
