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

    //Insertion d'un employée
    @Insert
    public abstract long insert (EmployeeEntity employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<EmployeeEntity> employees);

    //Update d'un employée
    @Update
    public abstract void update(EmployeeEntity employee);

    //Suppression d'un employée
    @Delete
    public abstract void delete(EmployeeEntity employee);


    //Select sur un employée
    @Query("SELECT * FROM employee WHERE id = :id")
    public abstract LiveData<EmployeeEntity> getById(String id);

    @Query("SELECT * FROM employee")
    public abstract LiveData<List<EmployeeEntity>> getAll();

    //Delete All
    @Query("DELETE FROM employee")
    public abstract void deleteAll();

}
