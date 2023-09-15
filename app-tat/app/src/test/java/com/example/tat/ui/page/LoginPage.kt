package com.example.tat.ui.page

import com.example.tat.ui.model.User
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class LoginPage(private val driver: AndroidDriver) : AbstractPage(driver) {

    @FindBy(id = "editTextEmailAddress")
    private val logInField: WebElement? = null

    @FindBy(id = "editTextPassword")
    private val passwordField: WebElement? = null

    @FindBy(id = "buttonLogin")
    private val logInButton: WebElement? = null

    fun login(user: User): HomePage {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(logInField))
        logInField?.sendKeys(user.login)
        passwordField?.sendKeys(user.password)
        clickOnLogInButton()
        return HomePage(driver = this.driver)
    }

    private fun clickOnLogInButton() {
        WebDriverWait(driver, Duration.ofMillis(10_000)).until(ExpectedConditions.elementToBeClickable(logInButton))
        logInButton?.click()
    }
}