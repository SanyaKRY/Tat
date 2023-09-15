package com.example.tat.ui.page

import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class HomePage(private val driver: AndroidDriver) : AbstractPage(driver) {

    @FindBy(id = "custom_title_image")
    private val titleImage: WebElement? = null

    fun isPageOpened(): Boolean? {
        return isTitleImageVisible()
    }

    private fun isTitleImageVisible(): Boolean? {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(titleImage))
        return titleImage?.isDisplayed
    }

    @FindBy(id = "name")
    private val userName: WebElement? = null

    @FindBy(id = "fullName")
    private val userFullName: WebElement? = null

    @FindBy(id = "role")
    private val userRole: WebElement? = null

    fun getUserNameFromPage(): String? {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(userName))
        return userName?.text
    }

    fun getUserFullNameFromPage(): String? {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(userFullName))
        return userFullName?.text
    }

    fun getUserRoleFromPage(): String? {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(userRole))
        return userRole?.text
    }

    @FindBy(id = "cardViewProject")
    private val cardViewProject: WebElement? = null

    @FindBy(id = "projectId")
    private val projects: List<WebElement>? = null

    fun getCountOfProjects(): Int? {
        WebDriverWait(driver, Duration.ofMillis(5_000)).until(ExpectedConditions.visibilityOf(cardViewProject))
        return projects?.size
    }
}