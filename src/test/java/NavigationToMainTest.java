import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pageobject.LoginPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NavigationToMainTest {
    private WebDriver driver;

    private final By button;

    public NavigationToMainTest(By button) {
        this.button = button;
    }

    @Parameterized.Parameters
    public static Object[][] Data() {
        return new Object[][]{
                {LoginPage.CONSTRUCTOR_BUTTON},
                {LoginPage.HEADER_LOGO}
        };
    }

    @Before
    public void setUp() {

/*        //яндекс
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
    }

    @Test
    @DisplayName("Переход на главную")
    public void navigatetoMainSuccsessfulRedirectTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until((ExpectedConditions.visibilityOfElementLocated(button)));
        loginPage.toMainButtonClick(button);
        assertTrue(loginPage.checkMainUrl());
    }
}