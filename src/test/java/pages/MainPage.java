package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    private WebDriver driver;

    // Конструктор для инициализации элементов страницы
    public MainPage(WebDriver driver) {
        this.driver = driver; // Сохраняем драйвер для работы с элементами
        PageFactory.initElements(driver, this);
    }

    // Локаторы элементов страницы с комментариями

    // Логотип Яндекс
    private By yandexLogo = By.cssSelector(".Header_LogoYandex__3TSOI img");
    // Логотип Scooter
    private By scooterLogo = By.cssSelector(".Header_LogoScooter__3lsAR img");
    // Кнопка "Заказать" в хедере
    private By orderButton = By.cssSelector(".Button_Button__ra12g");
    // Кнопка "Статус заказа"
    private By faqSection = By.cssSelector(".Home_FAQ__3uVm4");
    // Вторая кнопка "Заказать" (в основном контенте страницы)
    private By secondOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Кнопка для подтверждения использования cookie
    private By cookieAcceptButton = By.id("rcc-confirm-button");
    // Локатор для заголовка аккордеона
    private By accordionHeading(String headingId) {
        return By.xpath("//div[@id='" + headingId + "']");
    }
    // Локатор для текста внутри панели аккордеона
    private By accordionPanelText(String panelId) {
        return By.xpath("//div[@id='" + panelId + "']//p");
    }

    // Методы взаимодействия с элементами
    // Возвращает локатор кнопки "Заказать" в хедере
    public By getOrderButton() {
        return orderButton;
    }

    // Возвращает локатор второй кнопки "Заказать"
    public By getSecondOrderButton() {
        return secondOrderButton;
    }

    // Клик по кнопке согласия с cookie
    public void acceptCookies() {
        driver.findElement(cookieAcceptButton).click();
    }

    // Проверка, виден ли раздел FAQ
    public boolean isFAQSectionVisible() {
        return driver.findElement(faqSection).isDisplayed();
    }

    // Проверка, виден ли логотип Яндекс
    public boolean isYandexLogoVisible() {
        return driver.findElement(yandexLogo).isDisplayed();
    }

    // Проверка, виден ли логотип Scooter
    public boolean isScooterLogoVisible() {
        return driver.findElement(scooterLogo).isDisplayed();
    }
    // Метод для раскрытия аккордеона
    public void expandAccordion(String headingId) {
        WebElement heading = driver.findElement(accordionHeading(headingId));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", heading);
    }

    // Метод для проверки, что аккордеон раскрылся
    public boolean isAccordionExpanded(String headingId) {
        WebElement expandedHeading = driver.findElement(By.xpath("//div[@id='" + headingId + "' and @aria-expanded='true']"));
        return expandedHeading != null;
    }

    // Метод для получения текста панели аккордеона
    public String getAccordionPanelText(String panelId) {
        WebElement panelText = driver.findElement(accordionPanelText(panelId));
        return panelText.getText().trim();
    }
}
