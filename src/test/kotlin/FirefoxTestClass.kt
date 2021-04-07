import configuration.FirefoxDriverConfiguration
import helper.URL
import helper.loginMail
import helper.makeGoogleRequest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class FirefoxTestClass: FirefoxDriverConfiguration() {

    @Test
    fun secondGoogleLinkTest() {
        driver.run {
            get(URL.Google.url)
            makeGoogleRequest("first keys to send")
            val scndtLink = findElementByXPath(".//*[@class='g'][2]//a").getAttribute("href")
            get(scndtLink)
            pageSource shouldContain "SendKeys.Send(String) Method"
        }
    }

    @Test
    fun invalidPasswordLoginYandexTest() {
        driver.run {
            loginMail("AutotestLogin", "NoAutotestPassword")
            WebDriverWait(this, 1).until(
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
            WebDriverWait(this, 1).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".Textinput-Hint.Textinput-Hint_state_error")
                )
            )
            findElementByCssSelector(".Textinput-Hint.Textinput-Hint_state_error").text shouldBe "Такого аккаунта нет"
        }
    }
}