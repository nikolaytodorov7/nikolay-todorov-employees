package app.employeespair.util;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {
    private static final Pattern PATTERN_DDMMYYYY = Pattern.compile(
            "(0[1-9]|[12][0-9]|3[01])([-/.])(0[1-9]|1[012])([-/.])(19\\d{2}|20\\d{2})");
    private static final Pattern PATTERN_DDMMMYYYY = Pattern.compile(
            "(0[1-9]|[12][0-9]|3[01])([-/.])(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)([-/.])(19\\d{2}|20\\d{2})");
    private static final Pattern PATTERN_YYYYMMDD = Pattern.compile(
            "(19\\d{2}|20\\d{2})([-/.])(0[1-9]|1[012])([-/.])(0[1-9]|[12][0-9]|3[01])");
    private static final Pattern PATTERN_YYYYMMMDD = Pattern.compile(
            "(19\\d{2}|20\\d{2})([-/.])(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)([-/.])(0[1-9]|[12][0-9]|3[01])");
    private static final List<Pattern> DATE_PATTERNS = List.of(PATTERN_DDMMYYYY, PATTERN_YYYYMMDD, PATTERN_DDMMMYYYY, PATTERN_YYYYMMMDD);

    public LocalDate getLocalDate(int employeeId, String date, boolean starting) {
        if (!starting && date.equals("NULL"))
            return LocalDate.now();

        for (Pattern pattern : DATE_PATTERNS) {
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches())
                return getDate(employeeId, date, matcher);
        }

        String message = String.format("Date '%s' for employee with id: '%d' is invalid!", date, employeeId);
        throw new IllegalArgumentException(message);
    }

    private LocalDate getDate(int employeeId, String date, Matcher matcher) {
        validateDelimiters(employeeId, matcher);
        String datePartStr = matcher.group(1);
        String datePartStr2 = matcher.group(3);
        String datePartStr3 = matcher.group(5);
        int datePart = Integer.parseInt(datePartStr);
        int month = getMonth(datePartStr2);
        int datePart2 = Integer.parseInt(datePartStr3);
        LocalDate localDate;
        if (datePart > 31)
            localDate = LocalDate.of(datePart, month, datePart2);
        else
            localDate = LocalDate.of(datePart2, month, datePart);

        LocalDate dateNow = LocalDate.now();
        if (localDate.isBefore(dateNow))
            return localDate;

        String message = String.format("Provided date '%s' is in the future!", date);
        throw new IllegalArgumentException(message);
    }

    private int getMonth(String datePartStr2) {
        if (datePartStr2.length() == 2)
            return Integer.parseInt(datePartStr2);

        return switch (datePartStr2) {
            case "JAN" -> 1;
            case "FEB" -> 2;
            case "MAR" -> 3;
            case "APR" -> 4;
            case "MAY" -> 5;
            case "JUN" -> 6;
            case "JUL" -> 7;
            case "AUG" -> 8;
            case "SEP" -> 9;
            case "OCT" -> 10;
            case "NOV" -> 11;
            case "DEC" -> 12;
            default -> 0; // unreachable
        };
    }

    private void validateDelimiters(int employeeId, Matcher matcher) {
        String tokenDelimiter = matcher.group(2);
        String tokenDelimiter2 = matcher.group(4);
        if (tokenDelimiter2.equals(tokenDelimiter))
            return;

        String message = String.format("Different token delimiters '%s', '%s' provided for an employee with id: '%d'!",
                tokenDelimiter, tokenDelimiter2, employeeId);
        throw new IllegalArgumentException(message);
    }
}
