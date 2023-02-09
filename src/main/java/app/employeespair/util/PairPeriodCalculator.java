package app.employeespair.util;

import app.employeespair.model.Employee;
import app.employeespair.model.Period;
import app.employeespair.model.Result;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PairPeriodCalculator {
    private final List<Employee> employees;

    public PairPeriodCalculator(List<Employee> employees) {
        this.employees = employees;
    }

    public Result getLongestWorkingPairPeriod() {
        Result result = new Result();
        for (int i = 0; i < employees.size(); i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee employee1 = employees.get(i);
                Employee employee2 = employees.get(j);
                compareWorkingTime(employee1, employee2, result);
            }
        }

        return result;
    }

    private void compareWorkingTime(Employee employee1, Employee employee2, Result result) {
        Map<Integer, List<Period>> projectsWorkingTime1 = employee1.projectsWorkingTime;
        Map<Integer, List<Period>> projectsWorkingTime2 = employee2.projectsWorkingTime;
        Set<Integer> projectIds = projectsWorkingTime1.keySet();
        for (int projectId : projectIds) {
            if (!projectsWorkingTime2.containsKey(projectId))
                continue;

            List<Period> firstEmployeeWorkTime = employee1.projectsWorkingTime.get(projectId);
            List<Period> secondEmployeeWorkTime = employee2.projectsWorkingTime.get(projectId);
            for (int i = 0; i < firstEmployeeWorkTime.size(); i++) {
                for (int j = i; j < secondEmployeeWorkTime.size(); j++) {
                    Period workTimeEntry1 = firstEmployeeWorkTime.get(i);
                    LocalDate startTime1 = workTimeEntry1.from;
                    LocalDate endTime1 = workTimeEntry1.to;
                    Period workTimeEntry2 = secondEmployeeWorkTime.get(i);
                    LocalDate startTime2 = workTimeEntry2.from;
                    LocalDate endTime2 = workTimeEntry2.to;

                    if (startTime1.isAfter(endTime2) || startTime2.isAfter(endTime1))
                        return;

                    LocalDate start = startTime1.isAfter(startTime2) ? startTime1 : startTime2;
                    LocalDate end = endTime1.isBefore(endTime2) ? endTime1 : endTime2;
                    long daysWorked = start.until(end, ChronoUnit.DAYS) + 1;
                    if (result.daysWorkedTogether >= daysWorked)
                        continue;

                    result.daysWorkedTogether = daysWorked;
                    result.projectId = projectId;
                    result.firstEmployeeId = employee1.id;
                    result.secondEmployeeId = employee2.id;
                }
            }
        }
    }
}
