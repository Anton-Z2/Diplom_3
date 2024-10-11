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
import ru.yandex.praktikum.pageobject.MainPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NavigateToIngredientTest {
    private WebDriver driver;

    private By button;
    private By image;
    private int getX;
    private int getY;

    public NavigateToIngredientTest(By button, By image, int getX, int getY) {
        this.button = button;
        this.image = image;
        this.getX = getX;
        this.getY = getY;
    }

    @Parameterized.Parameters
    public static Object[][] Data() {
        return new Object[][]{
                {MainPage.BUN_BUTTON, MainPage.BUN_IMAGE, 24, 337},
                {MainPage.SAUSE_BUTTON, MainPage.SAUSE_IMAGE, 24, 668},
                {MainPage.FILLING_BUTTON, MainPage.FILLING_IMAGE, 24, 297}
        };
    }

    @Before
    public void setUp() {

/*        //яндекс
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDriver\\bin\\yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Работа\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        driver = new ChromeDriver(options);
*/
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка перехода к ингредиентам")
    public void moveToIngredientSuccsessfulmoveTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.ingredientButtonClick(button);
        assertTrue(mainPage.isElementInViewport(image, getX, getY));
    }

}
