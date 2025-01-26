package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.OrderPage;
import pages.MainPage;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class) // Указываем, что тест параметризирован
public class OrderTest {

    private WebDriver driver;
    private OrderPage orderPage;
    private MainPage mainPage;

    // Параметры, которые будем передавать в тест
    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String date;
    private final String period;
    private final String comment;

    // Конструктор для параметризированного теста
    public OrderTest(String name, String surname, String address, String phone, String date, String period, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.comment = comment;
    }

    // Указываем, какие данные будут использоваться для параметризации
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Ленина, д. 1", "+79001234567", "25.01.2025", "сутки", "Позвоните за 10 минут до доставки"},
                {"Петр", "Петров", "ул. Победы, д. 5", "+79007654321", "26.01.2025", "двое суток", "Оставьте на пороге"}
        });
    }

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        orderPage = new OrderPage(driver);
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testOrderFormSubmission() {
        // Открываем страницу заказа
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage.acceptCookies();
        java.util.List<WebElement> orderButtons = driver.findElements(By.xpath("//button[contains(text(), 'Заказать')]"));
        // Проверяем, что кнопок две
        Assert.assertEquals(2, orderButtons.size());
        // Кликаем на первую кнопку
        orderButtons.get(0).click();
        WebElement orderHeader = driver.findElement(By.className("Order_Header__BZXOb"));
        Assert.assertEquals("Для кого самокат", orderHeader.getText());
        // Заполняем данные клиента
        orderPage.enterName(name);
        orderPage.enterSurname(surname);
        orderPage.enterAddress(address);
        orderPage.selectMetroStation();
        orderPage.enterPhone(phone);
        orderPage.nextButton();
        // Заполняем данные аренды
        orderPage.enterDeliveryDate(date);
        orderPage.selectRentalPeriod(period);
        orderPage.selectBlackPearlColor();
        orderPage.enterComment(comment);
        // Отправляем заказ
        orderPage.submitOrder();
        // Подтверждаем заказ во всплывающем окне
        orderPage.clickYesInModal();
        // Проверяем сообщение об успешном оформлении
        String confirmationMessage = orderPage.getConfirmationMessage();
        assertTrue("Сообщение не содержит 'Заказ оформлен'", confirmationMessage.contains("Заказ оформлен"));
    }
}
