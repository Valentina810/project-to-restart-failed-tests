package tests;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodicallyFallingTests {

    @Test
    @AllureId("69")
    @DisplayName("Текущее время делится без остатка на 2")
    public void testTimeIsAMultipleOfTwo() {
        assertEquals(0, System.currentTimeMillis() % 2);
    }

    @Test
    @AllureId("70")
    @DisplayName("Текущее время делится без остатка на 3")
    public void testTimeIsAMultipleOfThree() {
        assertEquals(0, System.currentTimeMillis() % 3);
    }

    @Test
    @AllureId("71")
    @DisplayName("Текущее время делится без остатка на 4")
    public void testTimeIsAMultipleOfFour() {
        assertEquals(0, System.currentTimeMillis() % 4);
    }

    @Test
    @AllureId("72")
    @DisplayName("Текущее время делится без остатка на 5")
    public void testTimeIsAMultipleOfFive() {
        assertEquals(0, System.currentTimeMillis() % 5);
    }
}
