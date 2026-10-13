package net.engineeringdigest.JournalApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employeestestdb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTestDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private String email;

    private String department;

    private BigDecimal salary;


}
