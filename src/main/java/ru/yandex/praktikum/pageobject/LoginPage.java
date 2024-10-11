package ru.yandex.praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    public static final By EMAIL_FIELD = By.xpath(".//label[text() = 'Email']/parent::div/input[@class='text input__textfield text_type_main-default']");
    public static final By PASSWORD_FIELD = By.xpath(".//label[text() = 'Пароль']/parent::div/input[@class='text input__textfield text_type_main-default']");
    public static final By SIGN_IN = By.xpath(".//button[text() = 'Войти']");
    public static final By REGISTER_BUTTON = By.xpath(".//a[text() = 'Зарегистрироваться']");
    public static final By PASSWORD_RECOVERY = By.xpath(".//a[text() = 'Восстановить пароль']");
    public static final By CONSTRUCTOR_BUTTON = By.xpath(".//p[text() = 'Конструктор']");
    public static final By HEADER_LOGO = By.className("AppHeader_header__logo__2D0X2");
    private final WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы авторизации")
    public void open() {
        driver.get(LOGIN_PAGE_URL);
    }

    @Step("Переход на страницу регистрации")
    public void registerButtonClick() {
        driver.findElement(REGISTER_BUTTON).click();
    }

    @Step("Ввод email")
    public void enterEmail(String email) {
        driver.findElement(EMAIL_FIELD).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String pass) {
        driver.findElement(PASSWORD_FIELD).sendKeys(pass);
    }

    @Step("Клик по кнопке войти")
    public void signInButtonClick() {
        driver.findElement(SIGN_IN).click();
    }

    @Step("Клик по кнопке восстановления пароля")
    public void passwordRecoveryButtonClick() {
        driver.findElement(PASSWORD_RECOVERY).click();
    }

    @Step("Клик по кнопке перехода на главную)")
    public void toMainButtonClick(By button) {
        driver.findElement(button).click();
    }

    @Step("Проверка URL")
    public boolean checkMainUrl() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.urlToBe(MainPage.PAGE_URL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
