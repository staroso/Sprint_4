package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

public class SmokeTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void mainPageElementsAreDisplayed() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage.acceptCookies();
        // Проверяем, что логотипы Яндекс и Scooter видны
        Assert.assertTrue("Логотип Яндекс не отображается", mainPage.isYandexLogoVisible());
        Assert.assertTrue("Логотип Scooter не отображается", mainPage.isScooterLogoVisible());
        // Проверяем, что раздел FAQ виден
        Assert.assertTrue("Раздел FAQ не отображается", mainPage.isFAQSectionVisible());
        Assert.assertTrue("Кнопка 'Заказать' в хедере недоступна",
                driver.findElement(mainPage.getOrderButton()).isDisplayed());
        // Проверяем, что вторая кнопка "Заказать" видна
        Assert.assertTrue("Вторая кнопка 'Заказать' недоступна",
                driver.findElement(mainPage.getSecondOrderButton()).isDisplayed());
    }
}