package app.employeespair.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    public int id;
    public Map<Integer, List<Period>> projectsWorkingTime = new HashMap<>();

    public Employee(int id) {
        this.id = id;
    }

    public void addWorkTime(int projectId, LocalDate dateFrom, LocalDate dateTo) {
        List<Period> projectWorkingTime = projectsWorkingTime.computeIfAbsent(projectId, f -> new ArrayList<>());
        projectWorkingTime.add(new Period(dateFrom, dateTo));
    }
}
