package app.employeespair.service;

import app.employeespair.model.Employee;
import app.employeespair.model.Result;
import app.employeespair.util.PairPeriodCalculator;
import app.employeespair.util.ParserCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class PairService {
    public Result longestWorkingPair(Reader reader) throws IOException {
        CSVFormat csvFormat = CSVFormat
                .Builder
                .create()
                .setHeader()
                .setTrim(true)
                .build();

        CSVParser csvParser = csvFormat.parse(reader);
        ParserCSV parser = new ParserCSV();
        List<Employee> employees = parser.parseEmployeesFromCSV(csvParser);
        PairPeriodCalculator calculator = new PairPeriodCalculator(employees);
        return calculator.getLongestWorkingPairPeriod();
    }
}
