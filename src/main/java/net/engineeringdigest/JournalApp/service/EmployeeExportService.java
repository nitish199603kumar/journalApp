package net.engineeringdigest.JournalApp.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.engineeringdigest.JournalApp.Repository.EmployeesTestDbRepo;
import net.engineeringdigest.JournalApp.entity.EmployeeTestDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.ValueHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmployeeExportService {

    @Autowired
    private EmployeesTestDbRepo employeeRepository;

    @Value("${app.export.file-path}")
    private String filePath;



    public int exportEmployees() throws IOException {

//        // Step 1: Read all records from MySQL
        List<EmployeeTestDb> employees = employeeRepository.findAll();


        // Step 2: Prepare the output file path
        Path outputPath = Paths.get(filePath)
                .toAbsolutePath()
                .normalize();

        // Step 3: Create the parent directory if needed
        Path parent = outputPath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        // Step 4: Convert Java objects to JSON and write

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(outputPath.toFile(), employees);

        // Step 5: Return the number of exported records
        return employees.size();
    }

}