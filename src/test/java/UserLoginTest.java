import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.generator.UserGenerator;
import ru.yandex.praktikum.pageobject.*;
import ru.yandex.praktikum.pojo.User;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class UserLoginTest {
    private WebDriver driver;
    private User user;
    private final UserClient client = new UserClient();
    private final UserGenerator userGenerator = new UserGenerator();
    private String accessToken;

    @Before
    public void setUp() {

/*        //яндекс
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDriver\\bin\\yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Работа\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        driver = new ChromeDriver(options);*/

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        user = userGenerator.generateUser();
        ValidatableResponse response = client.createUser(user);
        accessToken = response.extract().jsonPath().getString("accessToken");
    }

    @After
    public void after() {

        driver.quit();


        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка входа через главную страницу")
    public void userLoginTestFromMainSuccsessfulAuthTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.open();
        mainPage.loginButtonClick();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();
        mainPage.lkButtonClick();
        assertEquals(user.getEmail(), profilePage.confirmOrderPopupGetText());
    }

    @Test
    @DisplayName("Проверка входа через Личный кабинет")
    public void userLoginTestFromLkSuccsessfulAuthTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();
        mainPage.lkButtonClick();
        assertEquals(user.getEmail(), profilePage.confirmOrderPopupGetText());
    }

    @Test
    @DisplayName("Проверка входа через форму регистрации")
    public void userLoginTestFromRegisterSuccsessfulAuthTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        loginPage.registerButtonClick();
        registerPage.loginClick();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();

        mainPage.lkButtonClick();
        assertEquals(user.getEmail(), profilePage.confirmOrderPopupGetText());
    }

    @Test
    @DisplayName("Проверка входа через форму восстановления пароля")
    public void userLoginTestFromPassRecoverySuccsessfulAuthTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        loginPage.passwordRecoveryButtonClick();
        passwordRecoveryPage.loginClick();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();
        mainPage.lkButtonClick();
        assertEquals(user.getEmail(), profilePage.confirmOrderPopupGetText());
    }
}
