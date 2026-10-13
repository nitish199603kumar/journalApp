package net.engineeringdigest.JournalApp.controller;


import net.engineeringdigest.JournalApp.service.EmployeeExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/general")
public class GeneralController {

    @Autowired
    private EmployeeExportService exportService;


    @PostMapping("/export/employeesdata")
    public ResponseEntity<Map<String, Object>> exportEmployees() {

        try {
            int count = exportService.exportEmployees();

            Map<String, Object> response = new LinkedHashMap<>();

            response.put("status", "success");
            response.put("message", "Employees exported successfully");
            response.put("recordsExported", count);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);


        } catch (IOException ex) {

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "error");
            response.put("message", "Unable to write JSON file");

            return ResponseEntity.status(
                            HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


}
