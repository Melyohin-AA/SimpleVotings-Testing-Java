package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;

public class Authentication {
	public static String login, password;
	public static boolean authenticated;
	
	public static void logIn(String login, String password) {
		Browser.driver.get(Pages.getLoginPageAddress());
		submitCredentials(login, password);
	}
	public static void submitCredentials(String login, String password) {
		Authentication.login = login;
		Authentication.password = password;
		Browser.driver.findElement(By.id("id_username")).sendKeys(login);
		Browser.driver.findElement(By.id("id_password")).sendKeys(password);
		Browser.driver.findElement(By.xpath("//button[@type='submit']")).click();
		authenticated = Browser.driver.getCurrentUrl().equals(Pages.getIndexPageAddress());
	}
	public static void logOut() {
		Browser.driver.get(Pages.getIndexPageAddress());
		clickLogOutLink();
	}
	public static void clickLogOutLink() {
		Browser.driver.findElement(By.xpath("//a[text()='Выйти']")).click();
		authenticated = false;
	}
	
	@Given("logged in as {string}:{string}")
	public void logged_in_as(String login, String password) {
		logIn(login, password);
	}
	
	@When("I try to log in as {string}:{string}")
	public void I_try_to_log_in_as(String login, String password) {
		submitCredentials(login, password);
	}
	@When("I try to log out")
	public void I_try_to_log_out() {
		clickLogOutLink();
	}
	
	@Then("I verify login is proper")
	public void I_verify_login_is_proper() {
		String actual = Browser.driver.findElement(By.xpath("//label[text()='Логин']/../input")).getAttribute("value");
		assert actual.equals(login);
	}
	@Then("I verify credentials are rejected")
	public void I_verify_credentials_are_rejected() {
		assert Browser.driver.getCurrentUrl().equals(Pages.getLoginPageAddress());
	}
	@Then("I verify I am authenticated")
	public void I_verify_I_am_authenticated() {
		assert Browser.driver.findElement(By.xpath("//a[text()='Выйти']")).isDisplayed();
	}
	@Then("I verify I am not authenticated")
	public void I_verify_I_am_not_authenticated() {
		assert Browser.driver.findElement(By.xpath("//a[text()='Войти']")).isDisplayed();
	}
}
