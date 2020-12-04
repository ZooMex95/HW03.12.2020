package com.homework.tests.card;

import com.homework.tests.base.BaseTest;

import org.junit.Assert;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CardTest extends BaseTest {
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"ИмяА", "ФамилияА", "ОтчествоА", "IMIAA FAMILIIAA"},
                {"НеИмя", "НеФамилия", "НеОтчество", "NEIMIA NEFAMILIIA"},
                {"Имя", "Фамилия", "Отчество", "IMIA FAMILIIA"}
        });
    }

    @Parameterized.Parameter(0)
    public CharSequence firstName;
    @Parameterized.Parameter(1)
    public CharSequence lastName;
    @Parameterized.Parameter(2)
    public CharSequence middleName;
    @Parameterized.Parameter(3)
    public CharSequence cardholder;

    @Test
    public void testDebetCard() {
        String cardsXPath = "//a[@aria-label='Меню  Карты']";
        WebElement cardsButton = driver.findElement(By.xpath(cardsXPath));
        cardsButton.click();

        String debetXPath = "//li[@class='kitt-top-menu__item']/a[text()='Дебетовые карты']";
        WebElement debetButton = driver.findElement(By.xpath(debetXPath));
        debetButton.click();

        String debetTitleXPath = "//h1[text()='Дебетовые карты']";
        WebElement debetTitle = driver.findElement(By.xpath(debetTitleXPath));
        Assert.assertEquals("Заголовок \"Дебетовые карты\" не обнаружен", "Дебетовые карты", debetTitle.getText());

        String molodezhnayaXPath = "//h2[text()='Молодёжная карта']";
        WebElement molodezhnaya = driver.findElement(By.xpath(molodezhnayaXPath));
        scrollToElement(molodezhnaya);
        molodezhnayaXPath = "//a[@data-product='Молодёжная карта'][@data-test-id='ProductCatalog_button']";
        molodezhnaya = driver.findElement(By.xpath(molodezhnayaXPath));
        molodezhnaya.click();

        String molodTitleXPath = "//h1[text()='Молодёжная карта']";
        WebElement molodTitle = driver.findElement(By.xpath(molodTitleXPath));
        Assert.assertEquals("Заголовок \"Молодёжная карта\" отсутствует", "Молодёжная карта", molodTitle.getText());

        String placeOrderXPath = "//a[@href='#order'][@data-test-id='PageTeaserDict_button']";
        WebElement placeOrder = driver.findElement(By.xpath(placeOrderXPath));
        scrollToElement(placeOrder);
        waitFor1Sec();
        placeOrder.click();

        String XPath = "//input[@name='odc-personal__lastName']";
        WebElement lastNameField = driver.findElement(By.xpath(XPath));
        scrollToElement(lastNameField);
        waitFor1Sec();
        lastNameField.click();
        lastNameField.sendKeys(new StringBuilder(lastName));
        fillInputField("firstName']", firstName);
        fillInputField("middleName']", middleName);
        fillInputField("birthDate']", "20.07.1995");
        fillInputField("email']","example@example.com");
        fillInputField("phone']","(999)123-45-67");
        Assert.assertEquals("Поле \"Владелец карты\" заполнено неверно", cardholder,
                        driver.findElement(By.xpath("//input[@id='odc-personal__cardName']")).getAttribute("value"));

        String nextButtonXPath = "//span[text()='Далее']";
        WebElement nextButton = driver.findElement(By.xpath(nextButtonXPath));
        nextButton.click();

        String issueDateErrorXPath = "//label[@for='odc-personal__issueDate']/../div[text()='Обязательное поле']";
        WebElement issueDateError = driver.findElement(By.xpath(issueDateErrorXPath));
        Assert.assertEquals("Ошибка не появилась поле \"Дата выдачи\"","Обязательное поле" ,issueDateError.getText());
        String personalNumberErrorXPath = "//label[@for='odc-personal__number']/../div[text()='Обязательное поле']";
        WebElement personalNumberError = driver.findElement(By.xpath(personalNumberErrorXPath));
        Assert.assertEquals("Ошибка не появилась поле \"Дата выдачи\"","Обязательное поле" ,personalNumberError.getText());
        String seriesErrorXPath = "//label[@for='odc-personal__series']/../div[text()='Обязательное поле']";
        WebElement seriesError = driver.findElement(By.xpath(seriesErrorXPath));
        Assert.assertEquals("Ошибка не появилась поле \"Дата выдачи\"","Обязательное поле" ,seriesError.getText());
    }


    private void scrollToElement(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitFor1Sec() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void fillInputField(String id, CharSequence text) {
        String XPath = "//input[@id='odc-personal__";
        WebElement currentField = driver.findElement(By.xpath(XPath.concat(id)));
        //waitFor1Sec();
        currentField.click();
        currentField.sendKeys(text);
        if (id.equals("phone']")){
            Assert.assertEquals("Поле \"" + id + "\" заполнено неверно", "+7 (999) 123-45-67", currentField.getAttribute("value"));
        }else {
            Assert.assertEquals("Поле \"" + id + "\" заполнено неверно", text, currentField.getAttribute("value"));
        }
    }


}
