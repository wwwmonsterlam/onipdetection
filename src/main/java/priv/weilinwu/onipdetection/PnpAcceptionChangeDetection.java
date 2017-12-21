package priv.weilinwu.onipdetection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PnpAcceptionChangeDetection extends Thread{
	public static final Logger logger = LoggerFactory.getLogger(PnpAcceptionChangeDetection.class);
	public  WebDriver driver;
	public final String keyword = "Stream have reached its registration intake limit";
	
	public PnpAcceptionChangeDetection() {
		System.setProperty("webdriver.chrome.driver", "D:\\Desktop\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();

	    //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public void run() {
		try {
			keywordChangeDetection();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void keywordChangeDetection() throws FileNotFoundException, JavaLayerException, InterruptedException {
		while(true) {
			launchWebsite();
			
			try {
				driver.findElement(By.xpath("//*[text()[contains(., '" + keyword + "')]]"));
			} catch(NoSuchElementException e) {
				// information found and play a song as alarm
				playASong();
			}

			Thread.sleep(5000);
		}
	}
	
	public void launchWebsite() {
		driver.navigate().to("https://www.services.citizenship.gov.on.ca/pnp_index/resources/app/guest/index.html");
//		// the following line is for testing purpose
//		String testFilePath = URLDecoder.decode(Main.class.getClassLoader().getResource("").getPath()) + "ha.html";
//		logger.info(testFilePath.substring(1));
//		driver.navigate().to(testFilePath.substring(1));
		
		while(true) {
			try {
				driver.findElement(By.xpath("//li[@class='active ng-binding']"));
			} catch (NoSuchElementException e) {
				logger.info("element not found~");
				continue;
			}
			
			// if the element above is found, it means the web page is successfully opened
			logger.info("ONIP web page is opened!~");
			break;
		}
	}
	
	public void playASong() throws FileNotFoundException, JavaLayerException {
		logger.info("Congratulation! ONIP web page information is changed!!!!!");
		new Player(new BufferedInputStream(new FileInputStream(new File("D:\\KwDownload\\song\\G.E.M.邓紫棋-喜欢你 (原唱- Beyond).mp3")))).play();
	}
}
