package ru.yandex.praktikum.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainPage {

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    public static final By LK_BUTTON = By.xpath(".//p[text() = 'Личный Кабинет']");
    public static final By MAIN_LOGIN_BUTTON = By.xpath(".//button[text() = 'Войти в аккаунт']");

    public static final By BUN_BUTTON = By.xpath(".//span[text() = 'Булки']/parent::div");
    public static final By BUN_IMAGE = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']");

    public static final By SAUSE_BUTTON = By.xpath(".//span[text() = 'Соусы']/parent::div");
    public static final By SAUSE_IMAGE = By.xpath(".//img[@alt='Соус Spicy-X']");

    public static final By FILLING_BUTTON = By.xpath(".//span[text() = 'Начинки']/parent::div");
    public static final By FILLING_IMAGE = By.xpath(".//img[@alt='Мясо бессмертных моллюсков Protostomia']");

    private final WebDriver driver;
    private static int counter = 1;


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие главной страницы")
    public void open() {
        driver.get(PAGE_URL);
    }

    @Step("Переход в личный кабинет")
    public void lkButtonClick() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LK_BUTTON));
        driver.findElement(LK_BUTTON).click();
    }

    @Step("Переход в личный кабинет")
    public void loginButtonClick() {
        driver.findElement(MAIN_LOGIN_BUTTON).click();
    }

    @Step("Переход к ингредиенту")
    public void ingredientButtonClick(By button) {
        driver.findElement(FILLING_BUTTON).click();
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

    @Step("Проверка URL")
    public boolean checkProfileUrl() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.urlToBe(ProfilePage.PROFILE_PAGE_URL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка видимости элемента")
    public boolean isElementInViewport(By image, int getX, int getY) {
        WebElement element = driver.findElement(image);
        return new WebDriverWait(driver, 3)
                .until(driver -> {
                    Rectangle rect = element.getRect();
                    Dimension windowSize = driver.manage().window().getSize();

                    System.out.println("проверка номер: " + counter++);
                    System.out.println("rect.getX(): " + rect.getX());
                    System.out.println("rect.getY(): " + rect.getY());
                    System.out.println("rect.getWidth(): " + rect.getWidth());
                    System.out.println("rect.getHeight(): " + rect.getHeight());
                    System.out.println("windowSize.getWidth(): " + windowSize.getWidth());
                    System.out.println("windowSize.getHeight(): " + windowSize.getHeight() + "\n");

                    // условие, которое проверяем внутри явного ожидания
                    return rect.getX() == getX
                            && rect.getY() == getY
                            && rect.getX() + rect.getWidth() <= windowSize.getWidth()
                            && rect.getY() + rect.getHeight() <= windowSize.getHeight();
                });
    }
}
