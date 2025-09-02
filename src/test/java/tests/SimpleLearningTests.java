package tests;

import org.junit.jupiter.api.*;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Аннотации. Учебные тесты")
public class SimpleLearningTests {

    @Test
    @DisplayName("Тест 1: Сайт открывается")
    void websiteOpensTest() {
        open("https://www.saucedemo.com");
        Assertions.assertEquals("Swag Labs", title());

    }


    @DisplayName("Тест 2: Логирование с валидными данными")
    @Test
    void successfulLoggingTest() {
        open("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(loginPage.isOnProductsPage());

    }

    @Disabled("Тест исключенный из работы")
    @Test
    @DisplayName("Отключенный тест")
    void disabledTest() {
        System.out.println("Этот тест не запустится");
    }
}
