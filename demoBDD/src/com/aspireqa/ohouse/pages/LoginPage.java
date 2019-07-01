package com.aspireqa.ohouse.pages;

import static ch.lambdaj.Lambda.convert;

import java.util.List;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aspireqa.ohouse.utilities.LoggerUtility;

import ch.lambdaj.function.convert.Converter;

@DefaultUrl("https://www.optionshouse.com/login")
public class LoginPage extends PageObject{
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	private LoggerUtility logger = LoggerUtility.getInstance(LoginPage.class);

	@FindBy(xpath= "//input[@id='username']")
    private WebElement username;

    @FindBy(xpath= "//input[@id='password']")
    private WebElement password;
    
    @FindBy(xpath= "//input[@id='loginSubmit']")
    private WebElement loginButton;

    public void enterUsername(String keyword) {
    	username.sendKeys(keyword);
    }
    
    public void enterPassword(String keyword) {
    	password.sendKeys(keyword);
    }

    public void login() {
    	loginButton.click();
    	//return switchToPage(HomePage.class);
    	
    	//this.switchToPage(HomePage.class).open();
    }

    public List<String> getDefinitions() {
        WebElementFacade definitionList = find(By.tagName("ol"));
        List<WebElement> results = definitionList.findElements(By.tagName("li"));
        return convert(results, toStrings());
    }

    private Converter<WebElement, String> toStrings() {
        return new Converter<WebElement, String>() {
            public String convert(WebElement from) {
                return from.getText();
            }
        };
    }


}
