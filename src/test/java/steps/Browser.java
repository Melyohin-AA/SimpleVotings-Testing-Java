package steps;

import java.time.Duration;
import java.util.Stack;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {
	public static boolean headless = true;
	public static WebDriver driver;
	public static Stack<String> tabHs = new Stack<String>();
	
	public static void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		if (headless) options.addArguments("--headless");
		driver = new ChromeDriver(options);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
		openBrowser();
	}
	
	@AfterAll
	public static void close() {
		driver.quit();
		driver = null;
	}
	
	@Before
	public static void reset() {
		tabHs.clear();
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
