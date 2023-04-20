package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NegativeCardApplicationTest {

    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void notFilledName() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79529676386");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void notFilledTelephone() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петр Петров");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }


    @Test
    void agreementNotFilled() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петр Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79529676386");
        driver.findElement(By.className("button__content")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void nameFilledIncorrectly() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Petr Petrov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79529676386");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void telephoneFilledIncorrectly() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петр Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7952967638");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }
}

