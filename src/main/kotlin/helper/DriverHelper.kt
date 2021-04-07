package helper

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit

class DriverHelper {

    fun createChromeDriver(): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe")

        val driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
        driver.manage().window().maximize()

        return driver
    }

    fun createFirefoxDriver(): FirefoxDriver {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe")

        val driver = FirefoxDriver()
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
        driver.manage().window().maximize()

        return driver
    }

}