package steps;

import java.time.Duration;
import java.util.Stack;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {
	public static boolean headless = false;
	public static WebDriver driver;
	public static Stack<String> tabHs = new Stack<String>();
	
	public static WebDriver openOne(boolean headless) {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	public static void newTab() {
		tabHs.push(driver.getWindowHandle());
		driver.switchTo().newWindow(WindowType.TAB);
	}
	public static void closeTab() {
		driver.close();
		driver.switchTo().window(tabHs.pop());
	}
	
	@BeforeAll
	public static void open() {
		driver = openOne(headless);
	}
	
	@AfterAll
	public static void close() {
		driver.quit();
		driver = null;
	}
	
	@Before
	public static void reset() {
		driver.manage().deleteAllCookies();
		String mainWindowH = driver.getWindowHandle();
		for (String windowH : driver.getWindowHandles()) {
		    if(!mainWindowH.contentEquals(windowH)) {
		        driver.switchTo().window(windowH);
		        driver.close();
		    }
		}
		driver.switchTo().window(mainWindowH);
	}
}
