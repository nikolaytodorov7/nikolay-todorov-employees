package app.employeespair.controller;

import app.employeespair.service.PairService;
import app.employeespair.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

@RequestMapping("/employees-pair")
@RestController
public class PairController {
    @Autowired
    PairService pairService;

    @PostMapping("/longest")
    public Result getLongestWorkingPairPeriod(@RequestParam("file") MultipartFile file) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
        return pairService.longestWorkingPair(inputStreamReader);
    }
}
