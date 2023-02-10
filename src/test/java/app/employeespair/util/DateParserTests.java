package app.employeespair.util;

import app.employeespair.util.DateParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateParserTests {
    private static LocalDate date;
    private static DateParser dateParser;

    @BeforeAll
    public static void init() {
        date = LocalDate.of(2001, 1, 1);
        dateParser = new DateParser();
    }

    @Test
    @DisplayName("date DD-MM-YYYY")
    public void test1() {
        LocalDate localDate = dateParser.getLocalDate(0, "01-01-2001", false);
        assertEquals(localDate, date);
    }

    @Test
    @DisplayName("date YYYY-MM-DD")
    public void test2() {
        LocalDate localDate = dateParser.getLocalDate(0, "2001-01-01", false);
        assertEquals(localDate, date);
    }

    @Test
    @DisplayName("date DD-MMM-YYYY")
    public void test3() {
        LocalDate localDate = dateParser.getLocalDate(0, "01-JAN-2001", false);
        assertEquals(localDate, date);
    }

    @Test
    @DisplayName("date YYYY-MMM-DD")
    public void test4() {
        LocalDate localDate = dateParser.getLocalDate(0, "2001-JAN-01", false);
        assertEquals(localDate, date);
    }

    @Test
    @DisplayName("exception - date NULL before")
    public void test5() {
        assertThrows(IllegalArgumentException.class, () -> dateParser.getLocalDate(0, "NULL", true));
    }

    @Test
    @DisplayName("date NULL after")
    public void test6() {
        assertDoesNotThrow(() -> dateParser.getLocalDate(0, "NULL", false));
    }

    @Test
    @DisplayName("date with delimiter '/'")
    public void test7() {
        LocalDate localDate = dateParser.getLocalDate(0, "12/12/2020", false);
        assertNotNull(localDate);
    }

    @Test
    @DisplayName("date with delimiter '.'")
    public void test8() {
        LocalDate localDate = dateParser.getLocalDate(0, "12.12.2020", false);
        assertNotNull(localDate);
    }

    @Test
    @DisplayName("date with delimiter '-'")
    public void test9() {
        LocalDate localDate = dateParser.getLocalDate(0, "12-12-2020", false);
        assertNotNull(localDate);
    }

    @Test
    @DisplayName("date with different delimiters")
    public void test10() {
        assertThrows(IllegalArgumentException.class, () -> dateParser.getLocalDate(0, "12-12.2020", true));
    }

    @Test
    @DisplayName("not full date")
    public void test11() {
        assertThrows(IllegalArgumentException.class, () -> dateParser.getLocalDate(0, "12-2011", true));
    }

    @Test
    @DisplayName("invalid string month date")
    public void test12() {
        assertThrows(IllegalArgumentException.class, () -> dateParser.getLocalDate(0, "01-INV-2011", true));
    }
}
