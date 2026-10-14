package net.engineeringdigest.JournalApp.controller;


import net.engineeringdigest.JournalApp.service.EmployeeExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/download/employeesjsonfile")
    public ResponseEntity<Resource> downloadEmployeesJsonFile()
            throws IOException {

        Resource resource = exportService.downloadEmployeesJsonFile();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"employees.json\""
                )
                .body(resource);
    }

//    public static void main(String[] args) {
//        String str="[ {\n" +
//                "  \"id\" : 1,\n" +
//                "  \"name\" : \"Rahul Sharma\",\n" +
//                "  \"email\" : \"rahul@example.com\",\n" +
//                "  \"department\" : \"IT\",\n" +
//                "  \"salary\" : 55000.00\n" +
//                "}, {\n" +
//                "  \"id\" : 2,\n" +
//                "  \"name\" : \"Priya Singh\",\n" +
//                "  \"email\" : \"priya@example.com\",\n" +
//                "  \"department\" : \"HR\",\n" +
//                "  \"salary\" : 45000.00\n" +
//                "}, {\n" +
//                "  \"id\" : 3,\n" +
//                "  \"name\" : \"Amit Kumar\",\n" +
//                "  \"email\" : \"amit@example.com\",\n" +
//                "  \"department\" : \"Finance\",\n" +
//                "  \"salary\" : 60000.00\n" +
//                "} ]";
//        // how to convert this string to list of EmployeeTestDb objects in java
//        // You can use the Jackson library to convert the JSON string to a list of EmployeeTestDb objects. Here's how you can do it:
//        // 1. Add the Jackson dependency to your project if you haven't already. If you're using Maven, add the following to your pom.xml:
//        // <dependency>
//        //     <groupId>com.fasterxml.jackson.core</groupId>
//        //     <artifactId>jackson-databind</artifactId>
//        //     <version>2.12.3</version>
//        // </dependency>
//        // 2. Use the ObjectMapper class to read the JSON string and convert it to a list of EmployeeTestDb objects. Here's an example:
//        // import com.fasterxml.jackson.core.type.TypeReference;
//        // import com.fasterxml.jackson.databind.ObjectMapper;
//        // import java.io.IOException;
//        // import java.util.List;
//        //
//        // ObjectMapper objectMapper = new ObjectMapper();
//        // try {
//        //     List<EmployeeTestDb> employees = objectMapper.readValue(str, new TypeReference<List<EmployeeTestDb>>() {});
//        // } catch (IOException e) {
//        //     e.printStackTrace();
//        // }


}






