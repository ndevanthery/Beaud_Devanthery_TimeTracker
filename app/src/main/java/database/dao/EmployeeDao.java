package database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import database.entity.Employee;

@Dao
public interface EmployeeDao {

    @Insert
    void addEmployee(Employee employee);

    @Query("select * from Employee")
    List<Employee> getAllEmployeeData();
}
