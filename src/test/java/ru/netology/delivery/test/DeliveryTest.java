package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $x("//*[@data-test-id=\"city\"]//input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id=\"date\"]//input").setValue(firstMeetingDate);
        $x("//*[@data-test-id=\"name\"]//input").setValue(validUser.getName());
        $x("//*[@data-test-id=\"phone\"]//input").setValue(validUser.getPhone());
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        $x("//*[contains(text(), \"Успешно!\")]").should(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id=\"success-notification\"]//div[contains(text(),\"Встреча успешно запланирована на \")]")
                .shouldBe(Condition.visible).shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id=\"date\"]//input").setValue(secondMeetingDate);
        $x("//span[@class=\"button__content\"]").click();
        $x("//*[@data-test-id=\"replan-notification\"]//span[text()=\"Перепланировать\"]").click();
        $x("//*[@data-test-id=\"success-notification\"]//div[contains(text(),\"Встреча успешно запланирована на \")]")
                .shouldBe(Condition.visible).shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}