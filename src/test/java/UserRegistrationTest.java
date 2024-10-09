import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.generator.UserGenerator;
import ru.yandex.praktikum.pageobject.LoginPage;
import ru.yandex.praktikum.pageobject.MainPage;
import ru.yandex.praktikum.pageobject.RegisterPage;
import ru.yandex.praktikum.pojo.User;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserRegistrationTest {

    private WebDriver driver;
    private User user;
    private final UserClient client = new UserClient();
    private String accessToken;


    @Before
    public void setUp() {

/*      //яндекс
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDriver\\bin\\yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Работа\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        driver = new ChromeDriver(options);*/

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.quit();

        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void checkUserRegistrationSuccessfulRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        loginPage.registerButtonClick();
        user = UserGenerator.generateUser();
        registerPage.enterName(user.getName());
        registerPage.enterEmail(user.getEmail());
        registerPage.enterPassword(user.getPassword());
        registerPage.confirmRegisterClick();
        assertTrue(registerPage.checkLoginUrl());
        accessToken = client.loginUser(user).extract().jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Проверка регистрации с некорректным паролем")
    public void checkUserRegistrationIncorrectPasswordTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        loginPage.registerButtonClick();
        user = UserGenerator.generateUserBadPass();
        registerPage.enterName(user.getName());
        registerPage.enterEmail(user.getEmail());
        registerPage.enterPassword(user.getPassword());
        registerPage.confirmRegisterClick();
        assertEquals("Некорректный пароль", registerPage.getMessage());
    }

}
