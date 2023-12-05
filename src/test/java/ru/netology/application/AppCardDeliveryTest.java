package ru.netology.application;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AppCardDeliveryTest {
    public String userDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");

    }

    @Test
    void shouldTestFormSendSuccessful() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Москва");
        String currentDate = userDate(4, "dd.MM.YYYY");
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id=date] input").sendKeys(currentDate);
        form.$("[data-test-id=name] input").setValue("Сергей Петров-Васильев");
        form.$("[data-test-id=phone] input").setValue("+79999999999");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + currentDate));
    }

}