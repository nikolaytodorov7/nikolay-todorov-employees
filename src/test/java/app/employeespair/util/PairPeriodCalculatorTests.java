package app.employeespair.util;

import app.employeespair.model.Employee;
import app.employeespair.model.Result;
import app.employeespair.util.PairPeriodCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairPeriodCalculatorTests {

    @Test
    @DisplayName("days worked together calculation")
    public void test1() {
        Employee employee = new Employee(0);
        employee.addWorkTime(4, LocalDate.of(2010, 2, 5), LocalDate.of(2010, 5, 5));
        Employee employee2 = new Employee(1);
        employee2.addWorkTime(4, LocalDate.of(2010, 1, 5), LocalDate.of(2010, 3, 1));
        List<Employee> employees = List.of(employee, employee2);

        PairPeriodCalculator calculator = new PairPeriodCalculator(employees);
        Result result = calculator.getLongestWorkingPairPeriod();
        assertEquals(result.daysWorkedTogether, 25);
    }
}
