import configuration.ChromeDriverConfiguration
import helper.URL
import helper.makeGoogleRequest
import helper.getMoreElement
import helper.loginMail
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.string.shouldContain
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait

class ChromeTestClass: ChromeDriverConfiguration() {

    @Test
    fun firstGoogleLinkTest() {
        driver.run {
            get(URL.Google.url)
            makeGoogleRequest("first keys to send")
            val firstLink = findElementByCssSelector(".g a").getAttribute("href")
            get(firstLink)
            pageSource shouldContain "Sends simulated keystrokes and mouse clicks to the"
        }
    }

    @Test
    fun changeLocationYandexTest() {
        driver.run {
            get(URL.Yandex.url)
            val location = findElementByCssSelector(".geolink__reg")
            location.text shouldBe "Минск"
            val moreMinsk = getMoreElement()
            location.click()
            findElementByCssSelector(".checkbox__label").click()
            val input = findElementByCssSelector(".input__control.input__input")
            input.clear()
            input.sendKeys("Лондон")
            findElementByXPath(".//*[@class='popup__items input__popup-items']//li[1]").click()
            findElementByCssSelector(".geolink__reg").text shouldBe "Лондон"
            val moreLondon = getMoreElement()
            assertThat(moreLondon).usingRecursiveComparison().ignoringFields("id").isEqualTo(moreMinsk)
        }
    }

    @Test
    fun successfullLoginYandexTest() {
        driver.run {
            loginMail("AutotestLogin", "autotestPassword123")
            findElementByCssSelector(".username.desk-notif-card__user-name").text shouldBe "AutotestLogin"
        }
    }

    @Test
    fun successfullLogoutYandexTest() {
        driver.run {
            loginMail("AutotestLogin", "autotestPassword123")
            findElementByCssSelector(".home-link.usermenu-link__control.home-link_black_yes").click()
            findElementByXPath(".//a[@class='menu__item usermenu__item menu__item_type_link'][@aria-label='Выйти']").click()
            findElementByCssSelector(".home-link.desk-notif-card__login-new-item.desk-notif-card__login-new-item_enter")
        }
    }

    @Test
    fun checkYandexServicesTest() {
        val servicesMap = mapOf("Видео" to "video",
            "Картинки" to "images",
            "Новости" to "news",
            "Карты" to "maps",
            "Маркет" to "market",
            "Переводчик" to "translate",
            "Музыка" to "music")
        driver.run {
            get(URL.Yandex.url)
            servicesMap.forEach {
                findElementByXPath(".//*[text()='${it.key}']/..").click()
                switchTo().window(windowHandles.toList().last())
                findElementByXPath(".//body")
                currentUrl shouldContain it.value
                close()
                switchTo().window(windowHandles.toList().first())
            }
        }
    }

    @Test
    fun changeLanguageYandexTest() {
        driver.run {
            get(URL.Yandex.url)
            findElementByXPath(".//a[@title='Выбрать язык']").click()
            findElementByXPath(".//a[@data-statlog='head.lang.more']").click()
            findElementByCssSelector(".select__button").click()
            findElementByXPath(".//div[@class='popup__content']//*[text()='English']").click()
            findElementByCssSelector(".button.form__save ").click()
            findElementByCssSelector(".home-link.desk-notif-card__login-new-item.desk-notif-card__login-new-item_enter").click()
            findElementByXPath(".//html").getAttribute("lang") shouldBe "en"
        }
    }
}