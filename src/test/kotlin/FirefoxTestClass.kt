import configuration.FirefoxDriverConfiguration
import helper.*
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions
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
            loginYandex("AutotestLogin", "NoAutotestPassword")
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

    @Test
    fun sortByPriceYandexMarketTest() {
        driver.run {
            goToYandexMarket()
            findElementByXPath(".//*[text()='Электроника']").click()
            findElementByXPath(".//*[text()='Экшн-камеры']").click()
            findElementByXPath(".//a[text()='Экшн-камеры']").click()
            findElementByXPath(".//button[@data-autotest-id='dprice']").click()
            findElementByXPath(".//button[@data-autotest-id='aprice']").click()
            Thread.sleep(2000)
            val listOfProducts = findElementsByXPath(".//div[@data-zone-name='snippetList']/article")
            var price = listOfProducts[0].getProductPriceFromYandexMarket()
            listOfProducts.forEach {
                val currentPrice = it.getProductPriceFromYandexMarket()
                currentPrice shouldBeLessThanOrEqualTo price
                price = currentPrice
            }
        }
    }

    @Test
    fun playAudioYandexMusciTest() {
        driver.run {
            goToYandexMusic()
            findElementByCssSelector(".d-input__field.deco-input").sendKeys("Beyo")
            findElementByXPath(".//*[@class='d-suggest-item__title-main'][text()='Beyoncé']/../../a[@class='d-suggest-item__wrapper-link']").click()
            Actions(this).moveToElement(findElementByCssSelector(".d-track__cover")).perform()
            findElementByXPath(".//div[contains(@class, 'd-track')]//button[contains(@class, 'button-play')]").click()
            Thread.sleep(2000)
            findElementByCssSelector(".progress__bar.progress__text").getAttribute("data-played-time").toDouble() shouldBeGreaterThan 0.0
        }
    }
}