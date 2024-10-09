package ru.yandex.praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    public static final By NAME_FIELD = By.xpath(".//label[text() = 'Имя']/parent::div/input[@class='text input__textfield text_type_main-default']");
    public static final By EMAIL_FIELD = By.xpath(".//label[text() = 'Email']/parent::div/input[@class='text input__textfield text_type_main-default']");
    public static final By PASSWORD_FIELD = By.xpath(".//label[text() = 'Пароль']/parent::div/input[@class='text input__textfield text_type_main-default']");
    public static final By CONFIRM_REGISTER_BUTTON = By.className("button_button__33qZ0");
    public static final By REGISTER_LOGIN_BUTTON = By.xpath(".//a[text() = 'Войти']");
    public static final By BAD_PASSWORD = By.xpath(".//p[text() = 'Некорректный пароль']");

    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод имени")
    public void enterName(String name) {
        driver.findElement(NAME_FIELD).sendKeys(name);
    }

    @Step("Ввод email")
    public void enterEmail(String email) {
        driver.findElement(EMAIL_FIELD).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String pass) {
        driver.findElement(PASSWORD_FIELD).sendKeys(pass);
    }

    @Step("Подверждение регистрации")
    public void confirmRegisterClick() {
        driver.findElement(CONFIRM_REGISTER_BUTTON).click();
    }

    @Step("Переход на страницу авторизации")
    public void loginClick() {
        driver.findElement(REGISTER_LOGIN_BUTTON).click();
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

    @Step("Получение текста ошибки")
    public String getMessage() {
        return driver.findElement(BAD_PASSWORD).getText();
    }
}
