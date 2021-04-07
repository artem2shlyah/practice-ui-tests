package helper

import org.openqa.selenium.remote.RemoteWebDriver

fun RemoteWebDriver.makeGoogleRequest(message: String) {
    val input = findElementByCssSelector("input[title='Поиск']")
    val button = findElementByXPath(".//input[@value='Поиск в Google']")
    input.clear()
    input.sendKeys(message)
    button.submit()
}