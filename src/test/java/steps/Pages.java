package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Pages {
	public static String serverAddress = "http://127.0.0.1:8000";
	
	public static String getApiPageAddress(String cmdName) {
		return serverAddress + "/testing_api/?cmd=" + cmdName;
	}
	public static String getIndexPageAddress() {
		return serverAddress + "/";
	}
	public static String getLoginPageAddress() {
		return serverAddress + "/login/";
	}
	public static String getRegistrationPageAddress(boolean test) {
		if (test) return serverAddress + "/registration/?test";
		return serverAddress + "/registration/";
	}
	public static String getMyProfilePageAddress() {
		return serverAddress + "/my_profile/";
	}
	
	@Given("{string} page is opened")
	public void page_is_opened(String url) {
		Browser.driver.get(url);
	}
	@Given("index page is opened")
	public void index_page_is_opened() {
		Browser.driver.get(getIndexPageAddress());
	}
	@Given("login page is opened")
	public void login_page_is_opened() {
		Browser.driver.get(getLoginPageAddress());
	}
	@Given("registration page is opened")
	public void registration_page_is_opened() {
		Browser.driver.get(getRegistrationPageAddress(true));
	}
	@Given("my_profile page is opened")
	public void my_profile_page_is_opened() {
		Browser.driver.get(getMyProfilePageAddress());
	}
	
	@When("I navigate to login page")
	public void I_navigate_to_login_page() {
		Browser.driver.findElement(By.xpath("//a[text()='Войти']")).click();
	}
	@When("I navigate to registration page")
	public void I_navigate_to_registration_page() {
		Browser.driver.findElement(By.xpath("//a[text()='Регистрация']")).click();
	}
	@When("I navigate to my_profile page")
	public void I_navigate_to_my_profile_page() {
		Browser.driver.findElement(By.xpath("//a[text()='Мой профиль']")).click();
	}
	
	@Then("I verify index page is loaded")
	public void I_verify_index_page_is_loaded() {
		assert Browser.driver.getCurrentUrl().equals(getIndexPageAddress());
	}
	@Then("I verify login page is loaded")
	public void I_verify_login_page_is_loaded() {
		assert Browser.driver.getCurrentUrl().equals(getLoginPageAddress());
	}
	@Then("I verify registration page is loaded")
	public void I_verify_registration_page_is_loaded() {
		assert Browser.driver.getCurrentUrl().equals(getRegistrationPageAddress(false));
	}
}
