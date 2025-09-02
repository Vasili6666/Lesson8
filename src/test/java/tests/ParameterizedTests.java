package tests;

import com.codeborne.selenide.SelenideElement;
import enums.FilterOptions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pages.LoginPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

@DisplayName("Параметризованные тесты для Saucedemo")
public class ParameterizedTests {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        open("https://www.saucedemo.com");
        loginPage = new LoginPage();
    }

    // 1. ТЕСТ С ОДНИМ АРГУМЕНТОМ - @ValueSource
    @DisplayName("Тест входа с разными логинами")
    @ParameterizedTest(name = "Логин: {0}")
    @ValueSource(strings = {
            "standard_user",
            "locked_out_user"
    })
        void loginWithDifferentUsernamesTest(String username) {
        loginPage.login(username, "secret_sauce");
        Assertions.assertTrue(loginPage.isOnProductsPage() || loginPage.isOnLoginPage());
    }

    // 2. ТЕСТ С НЕСКОЛЬКИМИ АРГУМЕНТАМИ - @CsvSource
    @DisplayName("Тест входа с разными комбинациями")
    @ParameterizedTest(name = "User: {0} | Pass: {1} | Should work: {2}")
    @CsvSource({
            "standard_user, secret_sauce, true",
            "locked_out_user, secret_sauce, false"
    })
    void loginWithMultipleParams(String username, String password, boolean shouldLogin) {
        loginPage.login(username, password);

        if (shouldLogin) {
            Assertions.assertTrue(loginPage.isOnProductsPage(),
                    "Должен был войти успешно с логином: " + username);
        } else {
            Assertions.assertTrue(loginPage.isOnLoginPage() || loginPage.hasError(),
                    "Должен был показать ошибку для логина: " + username);
        }
    }

    // 3. ТЕСТ С ENUM - @EnumSource (фильтрация)
    @DisplayName("Тест фильтрации товаров")
    @ParameterizedTest(name = "Фильтр: {0}")
    @EnumSource(FilterOptions.class)
    void testProductFilters(FilterOptions filter) {

        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertTrue(loginPage.isOnProductsPage());
        SelenideElement filterDropdown = $(".product_sort_container");
        filterDropdown.selectOptionByValue(filter.getValue());
        Assertions.assertTrue(filterDropdown.isDisplayed());
        System.out.println("✅ Успешно выбран фильтр: " + filter);
    }
    // 4. ТЕСТ С МЕТОДОМ-ПРОВАЙДЕРОМ - @MethodSource
    @DisplayName("Тесты с методом-провайдером")
    @ParameterizedTest(name = "Тест #{index}: {0}")
    @MethodSource()
    void testWithMethodSource(String testDescription, String username, boolean shouldWork) {
        System.out.println("Запуск: " + testDescription);

        loginPage.login(username, "secret_sauce");

        if (shouldWork) {
            Assertions.assertTrue(loginPage.isOnProductsPage());
        } else {
            Assertions.assertFalse(loginPage.isOnProductsPage());
        }
    }

    private static Stream<Arguments> testWithMethodSource() {
        return Stream.of(
                Arguments.of("Обычный пользователь", "standard_user", true),
                Arguments.of("Проблемный пользователь", "problem_user", true),
                Arguments.of("Визуальный пользователь", "visual_user", true),
                Arguments.of("Заблокированный пользователь", "locked_out_user", false),
                Arguments.of("Несуществующий пользователь", "unknown_user", false)
        );
    }

    // 5. ОТКЛЮЧЕННЫЙ ТЕСТ - @Disabled
    @Disabled("Тест исключенный из работы")
    @DisplayName("Пофиксим баг в следующем спринте")
    @ParameterizedTest
    @ValueSource(strings = {"standard_user", "problem_user"})
    void disabledTest() {
        System.out.println("Этот тест не запустится");
    }

    @AfterEach
    void pageClose() {
        closeWebDriver();
    }
}