package app.employeespair.util;

import app.employeespair.model.Employee;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserCSV {
    private final DateParser dateParser = new DateParser();

    public List<Employee> parseEmployeesFromCSV(CSVParser parser) {
        Map<Integer, Employee> employees = new HashMap<>();
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord record : records) {
            String employeeIdStr = record.get("EmpID");
            int employeeId = getId(employeeIdStr, "employee");
            Employee employee = employees.get(employeeId);
            if (employee == null) {
                employee = new Employee(employeeId);
                employees.put(employeeId, employee);
            }

            String projectIdStr = record.get("ProjectID");
            int projectId = getId(projectIdStr, "job");

            String dateFrom = record.get("DateFrom");
            LocalDate localDateFrom = dateParser.getLocalDate(employeeId, dateFrom, true);
            String dateTo = record.get("DateTo");
            LocalDate localDateTo = dateParser.getLocalDate(employeeId, dateTo, false);
            employee.addWorkTime(projectId, localDateFrom, localDateTo);
        }

        return new ArrayList<>(employees.values());
    }

    private int getId(String idStr, String typeId) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            String message = String.format("Illegal %s id provided: %s", typeId, idStr);
            throw new IllegalArgumentException(message);
        }

        if (id > 0)
            return id;

        String message = String.format("Negative %s id provided: %s", typeId, idStr);
        throw new IllegalArgumentException(message);
    }
}
