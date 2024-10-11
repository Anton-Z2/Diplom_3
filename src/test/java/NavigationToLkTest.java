import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.generator.UserGenerator;
import ru.yandex.praktikum.pageobject.LoginPage;
import ru.yandex.praktikum.pageobject.MainPage;
import ru.yandex.praktikum.pageobject.ProfilePage;
import ru.yandex.praktikum.pojo.User;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class NavigationToLkTest {
    private WebDriver driver;
    private User user;
    private final UserClient client = new UserClient();
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

        user = UserGenerator.generateUser();
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
    @DisplayName("Переход неавторизованного пользователя в ЛК")
    public void navigationToLkNoAuthUserSuccsessfulRedirectTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.lkButtonClick();
        assertTrue(mainPage.checkLoginUrl());
    }

    @Test
    @DisplayName("Переход авторизованного пользователя в ЛК")
    public void navigationToLkAuthUserSuccsessfulRedirectTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.signInButtonClick();
        mainPage.lkButtonClick();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe(ProfilePage.PROFILE_PAGE_URL));
        assertTrue(mainPage.checkProfileUrl());
    }
}
