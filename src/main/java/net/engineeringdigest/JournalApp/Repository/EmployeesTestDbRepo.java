package net.engineeringdigest.JournalApp.Repository;

import net.engineeringdigest.JournalApp.entity.EmployeeTestDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesTestDbRepo extends JpaRepository<EmployeeTestDb, Long> {

}
