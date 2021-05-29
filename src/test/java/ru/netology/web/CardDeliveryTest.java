package ru.netology.web;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    public String getPlanDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterWithValidInfoAutocompleteDate() {
        String planDate = getPlanDate(3, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='name'] input").setValue("Ли Елена");
        $("[data-test-id= phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planDate));
    }

    @Test
    void shouldRegisterWithValidInfoManualDate() {
        String planDate = getPlanDate(4, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id= date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id= date] input").setValue(planDate);
        $("[data-test-id='name'] input").setValue("Ли Елена");
        $("[data-test-id= phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planDate));
    }
}
