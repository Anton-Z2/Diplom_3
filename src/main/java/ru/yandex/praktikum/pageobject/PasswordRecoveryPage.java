package ru.yandex.praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {

    public static final By PASSWORD_RECOVERY_LOGIN_BUTTON = By.xpath(".//a[text() = 'Войти']");
    private final WebDriver driver;
    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Переход на страницу авторизации")
    public void loginClick() {
        driver.findElement(PASSWORD_RECOVERY_LOGIN_BUTTON).click();
    }
}
