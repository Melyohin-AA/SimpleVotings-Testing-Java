package steps;

import java.util.Random;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;

public class Registration {
	public static String login, password;
	public static boolean toDelete;
	
	public String generateUniqueLogin(int minLength, int maxLength, int chMin, int chMax) throws Exception {
		Browser.newTab();
		Random rnd = new Random();
		String login;
		while (true) {
			int length = rnd.nextInt(maxLength - minLength + 1) + minLength;
			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				char ch = (char)(rnd.nextInt(chMax - chMin + 1) + chMin);
				sb.append(ch);
			}
			login = sb.toString();
			int status = ApiCommand.hasUser(login).execute();
			if (status != 242) break;
		}
		Browser.closeTab();
		return login;
	}
	public void submitUserRegistration(String login, String pw1, String pw2, String name) {
		Registration.login = login;
		Registration.password = pw1;
		Browser.driver.findElement(By.id("id_login")).sendKeys(login);
		Browser.driver.findElement(By.id("id_password1")).sendKeys(pw1);
		Browser.driver.findElement(By.id("id_password2")).sendKeys(pw2);
		Browser.driver.findElement(By.id("id_name")).sendKeys(name);
		Browser.driver.findElement(By.id("reg_button")).click();
	}
	
	@After
	public static void tryDelete(Scenario scenario) throws Exception {
		if (toDelete) {
			toDelete = false;
			int status = ApiCommand.delUser(login, password).execute();
			System.out.println("Account deletion: " + status);
		}
	}
	
	@When("I try register as login={string}, PW1={string}, PW2={string}, name={string}")
	public void I_try_register_as(String login, String pw1, String pw2, String name) {
		submitUserRegistration(login, pw1, pw2, name);
	}
	@When("I try register as unique PW1={string}, PW2={string}, name={string}")
	public void I_try_register_as_unique(String pw1, String pw2, String name) throws Exception {
		String login = generateUniqueLogin(1, 64, 32, 126);
		submitUserRegistration(login, pw1, pw2, name);
	}
	
	@Then("I verify registration is succeeded")
	public void I_verify_registration_is_succeeded() {
		assert Browser.driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).isDisplayed();
		toDelete = true;
	}
	@Then("I verify registration is rejected")
	public void I_verify_registration_is_rejected() {
		toDelete = true;
		try {
			assert !Browser.driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).isDisplayed();
		} catch (NoSuchElementException ex) { }
		toDelete = false;
	}
}
