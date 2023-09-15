package com.example.tat.ui

import com.example.tat.ui.model.User
import com.example.tat.ui.page.HomePage
import com.example.tat.ui.page.WelcomePage
import com.example.tat.ui.poll.UserPoll
import com.example.tat.ui.template.TestTemplate
import com.google.common.truth.Truth
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.nio.file.Paths

class LoginTest {

    private lateinit var driver: AppiumDriver
    private lateinit var users: UserPoll
    private lateinit var request: RequestSpecification

    @Before
    fun setUp() {
        users = UserPoll()
        request = RestAssured.given().baseUri("http://localhost:8080")
        val capabilities: DesiredCapabilities = DesiredCapabilities()
        capabilities.setCapability("appium:automationName", "UIAutomator2")
        capabilities.setCapability("appium:platformVersion", "11")
        capabilities.setCapability("appium:deviceName", "Pixel 2 API 30")
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability(
            "appium:app",
            "${Paths.get("").toAbsolutePath()}/build/outputs/apk/billable/debug/app-billable-debug.apk"
        )
        capabilities.setCapability("appium:appWaitForLaunch", "false")
        driver = AndroidDriver(URL("http://127.0.0.1:4724"), capabilities)
    }

    @Test
    fun loginTest() {
        lateinit var user: User
        TestTemplate()
            .addPrecondition {
                user = users.take()
            }
            .addTest {
                val homePage: HomePage = WelcomePage(driver as AndroidDriver).goToLoginPage().login(user)
                val isPageOpened = homePage.isPageOpened()
                Truth.assertThat(isPageOpened).isEqualTo(true)
            }
            .addPostCondition {
                users.realise(user)
            }
            .execute()
    }

    @Test
    fun userInfoTest() {
        lateinit var user: User
        lateinit var userNameApiResponse: String
        lateinit var fullNameApiResponse: String
        lateinit var roleApiResponse: String

        TestTemplate()
            .addPrecondition {
                user = users.take()
                val response: Response = request.get("api/v1/user/me")
                userNameApiResponse = response.jsonPath().get("username")
                fullNameApiResponse = response.jsonPath().get("fullName")
                roleApiResponse = response.jsonPath().get("role")

            }
            .addTest {
                val homePage: HomePage = WelcomePage(driver as AndroidDriver).goToLoginPage().login(user)
                val userNameFromPage = homePage.getUserNameFromPage()
                val userFullNameFromPage = homePage.getUserFullNameFromPage()
                val userRoleFromPage = homePage.getUserRoleFromPage()
                Truth.assertThat(userNameFromPage).isEqualTo(userNameApiResponse)
                Truth.assertThat(userFullNameFromPage).isEqualTo(fullNameApiResponse)
                Truth.assertThat(userRoleFromPage).isEqualTo(roleApiResponse)
            }
            .addPostCondition {
                users.realise(user)
            }
            .execute()
    }

    @Test
    fun userProjectsTest() {
        lateinit var user: User
        lateinit var projectsApiResponse: List<String>

        TestTemplate()
            .addPrecondition {
                user = users.take()
                val response: Response = request.get("api/v1/project")
                projectsApiResponse = response.jsonPath().get("content")
            }
            .addTest {
                val homePage: HomePage = WelcomePage(driver as AndroidDriver).goToLoginPage().login(user)
                val countOfProjects = homePage.getCountOfProjects()
                Truth.assertThat(countOfProjects).isEqualTo(projectsApiResponse.size)
            }
            .addPostCondition {
                users.realise(user)
            }
            .execute()
    }

    @After
    fun tearDown() {
        driver.quit()
    }
}