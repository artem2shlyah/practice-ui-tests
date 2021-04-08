package helper

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver


fun RemoteWebDriver.getMoreElement(): WebElement {
    findElementByCssSelector(".home-link.services-new__item.services-new__more").click()
    val more = findElementByXPath(".//*[@class='services-new__more-popup-content']")
    findElementByXPath(".//*[@class='services-new__more-popup-content']//*[@role='button']").click()
    return more
}

fun RemoteWebDriver.loginMail(user: String, password: String) {
    get(URL.Yandex.url)
    if (findElementsByCssSelector(".username.desk-notif-card__user-name").isEmpty()) { //todo подумать на оптимизацией implicitlyWait в этом месте
        findElementByCssSelector(".home-link.desk-notif-card__login-new-item.desk-notif-card__login-new-item_enter").click()
        findElementByCssSelector("#passp-field-login").sendKeys(user + Keys.ENTER)
        findElementByCssSelector("#passp-field-passwd").sendKeys(password + Keys.ENTER)
    }
}

fun RemoteWebDriver.goToYandexMarket() {
    get(URL.Yandex.url)
    findElementByXPath(".//*[text()='Маркет']/..").click()
    close()
    switchTo().window(windowHandles.toList().last())
}

fun RemoteWebDriver.addToCompareAndGetUrlProductByXpath(xpath: String): String {
    val product = findElementByXPath(xpath)
    product.findElement(By.xpath(".//*[@aria-label][2]")).click()

    return product.findElement(By.xpath(".//a")).getAttribute("href").substringBefore("?")
}

