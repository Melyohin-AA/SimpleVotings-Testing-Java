package steps;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.*;

public class ApiCommand {
	public String name;
	public HashMap<String, String> args;
	
	public ApiCommand(String name, HashMap<String, String> args) {
		this.name = name;
		this.args = args;
	}
	
	public int execute() throws Exception {
		Browser.driver.get(Pages.getApiPageAddress(name));
		String getStatus = Browser.driver.findElement(By.xpath("html/body")).getText();
		if (getStatus.equals("400"))
			throw new Exception("Command load has failed!");
		for (Map.Entry<String, String> pair : args.entrySet()) {
			String argXpath = "html/body/form/input[@name='" + pair.getKey() + "']";
			Browser.driver.findElement(By.xpath(argXpath)).sendKeys(pair.getValue());
		}
		Browser.driver.findElement(By.xpath("html/body/form/button")).click();
		int postStatus = Integer.parseInt(Browser.driver.findElement(By.xpath("html/body")).getText());
		if (postStatus == 400)
			throw new Exception("Command execution has failed!");
		return postStatus;
	}
	
	public static ApiCommand delUser(String login, String password) {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("login", login);
		args.put("password", password);
		return new ApiCommand("del_user", args);
	}
	public static ApiCommand hasUser(String login) {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("login", login);
		return new ApiCommand("has_user", args);
	}
}
