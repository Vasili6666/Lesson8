package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public void login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isOnLoginPage() {
        return loginButton.isDisplayed();
    }

    public boolean isOnProductsPage() {
        return webdriver().driver().url().contains("inventory");
    }

    public boolean hasError() {
        return errorMessage.exists();
    }
}
