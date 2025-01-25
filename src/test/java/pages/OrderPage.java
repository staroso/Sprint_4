package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    // Локаторы элементов страницы
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStation = By.xpath("//div[@class='select-search__value']/input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton  = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and text()='Далее']");
    private By deliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.xpath("//div[contains(text(), '* Срок аренды')]");
    private By blackPearlCheckbox = By.id("black");
    private By greyCheckbox = By.id("grey");
    private By commentField = By.cssSelector("input[placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']"); // Селектор с точками
    private By confirmationMessage = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");
    private By cookieButton = By.id("rcc-confirm-button");
    private By yesButton = By.xpath("//div[@class='Order_Modal__YZ-d3']//button[text()='Да']");


    // Конструктор
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void selectMetroStation() {
        // Кликаем на поле выбора станции метро
        WebElement metroField = driver.findElement(metroStation);
        metroField.click();

        // Ожидаем появления выпадающего списка и выбираем первый элемент
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='select-search__row'][1]")));

        // Кликаем на первый элемент списка
        firstOption.click();
    }

    public void enterPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void nextButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement button = driver.findElement(nextButton);

        button.click();
    }

    public void enterDeliveryDate(String date) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement deliveryDate = driver.findElement(deliveryDateField);

        deliveryDate.clear(); // Очистить поле перед вводом
        deliveryDate.sendKeys(date);
        deliveryDate.sendKeys(Keys.ENTER);// Вводим дату
    }

    public void selectRentalPeriod(String period) {
        WebElement dropdown = driver.findElement(rentalPeriodDropdown);
        dropdown.click(); // Открыть выпадающий список
        WebElement option = driver.findElement(By.xpath("//div[text()='" + period + "']"));
        option.click(); // Выбираем нужный период аренды
    }

    public void selectBlackPearlColor() {
        WebElement blackPearlCheckboxElement = driver.findElement(blackPearlCheckbox);
        if (!blackPearlCheckboxElement.isSelected()) { // Проверить, не выбран ли уже чекбокс
            blackPearlCheckboxElement.click(); // Если не выбран — выбрать
        }
    }

    public void enterComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void submitOrder() {
        driver.findElement(orderButton).click(); // Используем CSS-селектор с двумя классами через точку
    }


    public void clickYesInModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement yes = driver.findElement(yesButton);
        // Просто кликаем на кнопку "Да"
        yes.click();

    }

    public String getConfirmationMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Ожидаем, пока текст не появится в элементе
        wait.until(ExpectedConditions.textToBePresentInElementLocated(confirmationMessage, "Заказ оформлен"));
        return driver.findElement(confirmationMessage).getText();

    }
}

