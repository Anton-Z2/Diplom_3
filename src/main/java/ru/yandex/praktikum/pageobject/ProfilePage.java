package ru.yandex.praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    public static final String PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    public static final By EMAIL_FIELD = By.xpath(".//label[text() = 'Логин']/parent::div/input[@class='text input__textfield text_type_main-default input__textfield-disabled']");
    public static final By LOGOUT_BUTTON = By.xpath(".//button[text() = 'Выход']");

    private final WebDriver driver;
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получение email для проверки")
    public String confirmOrderPopupGetText() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        return driver.findElement(EMAIL_FIELD).getAttribute("value");
    }

    @Step("Подверждение регистрации")
    public void logoutButtonClick() {
        driver.findElement(LOGOUT_BUTTON).click();
    }

    @Step("Проверка URL")
    public boolean checkLoginUrl() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.urlToBe(LoginPage.LOGIN_PAGE_URL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}