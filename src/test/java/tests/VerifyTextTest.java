package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class VerifyTextTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String accordionHeadingId; // ID заголовка аккордеона
    private final String accordionPanelId;   // ID панели аккордеона
    private final String expectedText;       // Ожидаемый текст

    public VerifyTextTest(String accordionHeadingId, String accordionPanelId, String expectedText) {
        this.accordionHeadingId = accordionHeadingId;
        this.accordionPanelId = accordionPanelId;
        this.expectedText = expectedText;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"accordion__heading-0", "accordion__panel-0", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"accordion__heading-1", "accordion__panel-1", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"accordion__heading-2", "accordion__panel-2", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."}
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void verifyText() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage.acceptCookies();
        // Раскрываем аккордеон
        mainPage.expandAccordion(accordionHeadingId);
        // Убедиться, что аккордеон раскрылся
        assertTrue("Аккордеон не раскрылся", mainPage.isAccordionExpanded(accordionHeadingId));
        // Получаем текст панели и проверяем его
        String actualText = mainPage.getAccordionPanelText(accordionPanelId);
        assertTrue("Текст не совпадает", actualText.contains(expectedText));
    }
}
