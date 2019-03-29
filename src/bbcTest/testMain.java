package bbcTest;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testMain {
	public static String convertText(String s){
		s=s.toLowerCase().replaceAll("ý", "i");
		s=s.toLowerCase().replaceAll("ü", "u");
		s=s.toLowerCase().replaceAll("ö", "o");	
		s=s.toLowerCase().replaceAll("þ", "s");
		s=s.toLowerCase().replaceAll("ç", "c");
		s=s.toLowerCase().replaceAll("ð", "g");
		s=s.toLowerCase().replaceAll("ü", "u");
		s=s.toLowerCase().replaceAll("[^A-Za-z0-9]", "_");
		s=s.toLowerCase().replaceAll(" ", "-");
		
		return s;
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.bbc.com/turkce");
		
		List<WebElement> el = driver.findElements(By.className("faux-block-link"));
		/*for ( WebElement e : el ) {
			  System.out.println(e.getTagName());
		}*/
		System.out.println("toplam haber linki: "+ el.size());
		
		List<WebElement> linkDetailElements;
		PrintWriter writer;
		for (int i = 0; i < el.size(); i++) {
			el = driver.findElements(By.className("faux-block-link"));
			Thread.sleep(1000);
			el.get(i).click();
			Thread.sleep(1000);
			linkDetailElements = driver.findElements(By.tagName("p"));
			writer = new PrintWriter("news/"+convertText(driver.getTitle())+".txt", "UTF-8");
			writer.println(driver.getTitle());
			for (int j = 0; j < linkDetailElements.size(); j++) {
				writer.println(linkDetailElements.get(j).getText());
			}
			writer.println("");
			writer.println("kaynak: "+ driver.getCurrentUrl());
			writer.close();
			driver.navigate().back();
		}
		
		driver.quit();
	}

}
