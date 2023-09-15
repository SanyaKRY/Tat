package com.example.tat.ui.page

import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class WelcomePage(private val driver: AndroidDriver) : AbstractPage(driver) {

    @FindBy(id = "buttonLogin")
    private val logInButton: WebElement? = null

    fun goToLoginPage(): LoginPage {
        clickOnLogInButton()
        return LoginPage(driver = this.driver)
    }

    private fun clickOnLogInButton() {
        WebDriverWait(driver, Duration.ofMillis(10_000)).until(ExpectedConditions.elementToBeClickable(logInButton))
        logInButton?.click()
    }
}