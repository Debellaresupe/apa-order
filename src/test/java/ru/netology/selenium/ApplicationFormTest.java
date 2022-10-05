package ru.netology.selenium;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();

// убедитесь, что файл chromedriver.exe расположен именно в каталоге C:\tmp
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendSuccessfully() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());


    }
    @Test
    public void shouldNotSendIncorrectNameFormat() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());


    }

    @Test
    public void shouldNotSendEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());


    }

    @Test
    public void shouldNotSendIncorrectTelephoneFormat() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("9999999999");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid span.input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());


    }

    @Test
    public void shouldNotSendEmptyTelephone() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());


    }

    @Test
    public void shouldNotSendCheckboxIsNotPressed() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());


    }
}
