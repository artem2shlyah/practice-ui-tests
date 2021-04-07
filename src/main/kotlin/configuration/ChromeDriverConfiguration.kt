package configuration

import helper.DriverHelper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.chrome.ChromeDriver

open class ChromeDriverConfiguration {

    companion object {
        lateinit var driver: ChromeDriver

        @JvmStatic
        @AfterAll
        fun browserQuit() {
            driver.quit()
        }

        @JvmStatic
        @BeforeAll
        fun browserInit() {
            driver = DriverHelper().createChromeDriver()
        }
    }

    @AfterEach
    fun clean() {
        driver.executeScript("window.open()")
        driver.switchTo().window(driver.windowHandles.toList().first())
        driver.close()
        driver.switchTo().window(driver.windowHandles.toList().first())
        driver.manage().deleteAllCookies()
        driver.localStorage.clear()
        driver.sessionStorage.clear()
    }
}