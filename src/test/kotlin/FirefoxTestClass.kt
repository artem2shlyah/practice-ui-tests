import configuration.FirefoxDriverConfiguration
import helper.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class FirefoxTestClass: FirefoxDriverConfiguration() {
    private val waiter = WebDriverWait(driver, 2)

    @Test
    fun secondGoogleLinkTest() {
        driver.run {
            get(URL.Google.url)
            makeGoogleRequest("first keys to send")
            val scndLink = findElementByXPath(".//*[@class='g'][2]//a").getAttribute("href")
            get(scndLink)
        }
    }

    @Test
    fun invalidPasswordLoginYandexTest() {
        driver.run {
            loginMail("AutotestLogin", "NoAutotestPassword")
            waiter.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".Textinput-Hint.Textinput-Hint_state_error")
                )
            )
            findElementByCssSelector(".Textinput-Hint.Textinput-Hint_state_error").text shouldBe "Неверный пароль"
        }
    }

    @Test
    fun invalidUsernameLoginYandexTest() {
        driver.run {
            get(URL.Yandex.url)
            findElementByCssSelector(".home-link.desk-notif-card__login-new-item.desk-notif-card__login-new-item_enter").click()
            findElementByCssSelector("#passp-field-login").sendKeys("NoAutotestUser" + Keys.ENTER)
            waiter.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".Textinput-Hint.Textinput-Hint_state_error")
                )
            )
            findElementByCssSelector(".Textinput-Hint.Textinput-Hint_state_error").text shouldBe "Такого аккаунта нет"
        }
    }

    @Test
    fun deleteComparingPorductsTest() {
        driver.run {
            goToYandexMarket()
            val input = findElementByCssSelector("#header-search")
            input.sendKeys("Note 8" + Keys.ENTER)
            val firstProductUrl = addToCompareAndGetUrlProductByXpath(".//article[@data-autotest-id='product-snippet'][1]")
            val secondProductUrl = addToCompareAndGetUrlProductByXpath(".//article[@data-autotest-id='product-snippet'][2]")
            waiter.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@data-apiary-widget-name='@market/PopupInformer']"))
            )
            findElementByXPath(".//a[@href='/my/compare-lists']").click()
            waiter.until(ExpectedConditions.urlContains("/compare/"))
            val firstComparingProductUrl = findElementByXPath(".//div[@data-apiary-widget-name='@MarketNode/CompareContent']//a[@href][1]").getAttribute("href").substringBefore("?")
            val secondComparingProductUrl = findElementByXPath(".//div[@data-apiary-widget-name='@MarketNode/CompareContent']//a[@href][2]").getAttribute("href").substringBefore("?")
            firstComparingProductUrl shouldContain firstProductUrl
            secondComparingProductUrl shouldContain secondProductUrl
            findElementByXPath(".//button[text()='Удалить список']").click()
            waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@data-apiary-widget-name='@MarketNode/CompareContent']")))
        }
    }
}