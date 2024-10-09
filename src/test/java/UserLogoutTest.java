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
import ru.yandex.praktikum.pageobject.LoginPage;
import ru.yandex.praktikum.pageobject.MainPage;
import ru.yandex.praktikum.pageobject.ProfilePage;
import ru.yandex.praktikum.pojo.User;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class UserLogoutTest {
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
    @DisplayName("Проверка выхода пользователя")
    public void userLogoutSucsessfulLogoutTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        loginPage.open();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();
        mainPage.lkButtonClick();
        profilePage.logoutButtonClick();
        assertTrue(profilePage.checkLoginUrl());
    }
}
