package priv.weilinwu.onipdetection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PnpNoticeUpdateDetection extends Thread{
	public static final Logger logger = LoggerFactory.getLogger(PnpNoticeUpdateDetection.class);
	public final  By byDecember182017 = By.xpath("//span[text()='December 18, 2017']");
	public final  By byMainContent = By.className("main_content");
	public  WebDriver driver;
	
	public PnpNoticeUpdateDetection() {
		System.setProperty("webdriver.chrome.driver", "D:\\Desktop\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();

	    //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public void run() {
		try {
			noticUpdateDetection();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void noticUpdateDetection() throws FileNotFoundException, JavaLayerException {
		while(true) {
			launchWebsite();
			String today = getDate();
			ArrayList<WebElement> paragraphs = (ArrayList<WebElement>)driver.findElement(byMainContent).findElements(By.tagName("p"));
			for(int i = 0; i < 30; i++) {
				WebElement webElement = paragraphs.get(i);
				logger.info("checking the {" + i + "}th paragraph");
				try { 
					webElement.findElement(By.xpath(".//*[text()[contains(.,'Masters and PhD Graduate Streams')]]"));
				} catch (Exception e) {
					continue;
				}
				
				// information found and play a song as alarm
				playASong();
			}
		}
	}
	
	public String getDate() {
		Date now = Calendar.getInstance().getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
		String string = dateFormat.format(now);
		logger.info("Current date is: " + string);
		return string;
	}
	
	public void launchWebsite() {
		driver.navigate().to("http://www.ontarioimmigration.ca/en/pnp/OI_PNPNEW.html");
		// the following line is for testing purpose
//		String testFilePath = URLDecoder.decode(Main.class.getClassLoader().getResource("").getPath()) + "haha.html";
//		logger.info(testFilePath.substring(1));
//		driver.navigate().to(testFilePath.substring(1));
		
		while(true) {
			try { 
				driver.findElement(byDecember182017);
			} catch (NoSuchElementException e) {
				continue;
			}
			
			// if the element above is found, it means the web page is successfully opened
			logger.info("Notice web page is opened!~");
			break;
		}
	}
	
	public void playASong() throws FileNotFoundException, JavaLayerException {
		logger.info("Congratulation! Information is found!!!!!");
		new Player(new BufferedInputStream(new FileInputStream(new File("D:\\KwDownload\\song\\Kelly Clarkson-Because Of You.mp3")))).play();
	}

}
