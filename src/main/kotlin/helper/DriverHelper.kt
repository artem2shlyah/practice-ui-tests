package helper

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import java.util.concurrent.TimeUnit

class DriverHelper {

    fun createChromeDriver(): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe")

        val driver = ChromeDriver(ChromeOptions().addArguments("incognito"))
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
        driver.manage().window().maximize()

        return driver
    }

    fun createFirefoxDriver(): FirefoxDriver {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe")

        val profile = FirefoxProfile().apply {
            setPreference("browser.privatebrowsing.autostart",true)
        }
        val driver = FirefoxDriver(FirefoxOptions().setProfile(profile))
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
        driver.manage().window().maximize()

        return driver
    }

}