package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entity.EmployeeEntity;

@Dao
public abstract class EmployeeDao {

    //Insert employee
    @Insert
    public abstract long insert (EmployeeEntity employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<EmployeeEntity> employees);

    //Update employee
    @Update
    public abstract void update(EmployeeEntity employee);

    //Delete employee
    @Delete
    public abstract void delete(EmployeeEntity employee);


    //Select employee with username
    @Query("SELECT * FROM employee WHERE Username = :username")
    public abstract LiveData<EmployeeEntity> getById(String username);

    //Select all employees
    @Query("SELECT * FROM employee")
    public abstract LiveData<List<EmployeeEntity>> getAll();

    //Delete All employees
    @Query("DELETE FROM employee")
    public abstract void deleteAll();

}
