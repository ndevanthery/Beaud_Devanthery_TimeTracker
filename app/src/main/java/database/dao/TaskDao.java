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
import database.entity.TaskEntity;


@Dao
public abstract class TaskDao {
    //Insert task
    @Insert
    public abstract long insert (TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<TaskEntity> tasks);

    //Update task
    @Update
    public abstract void update(TaskEntity task);

    //Delete task
    @Delete
    public abstract void delete(TaskEntity task);

    //Select task with id
    @Query("SELECT * FROM task WHERE id = :id")
    public abstract LiveData<TaskEntity> getById(Long id);

    //Select all tasks
    @Query("SELECT * FROM task")
    public abstract LiveData<List<TaskEntity>> getAll();

    //delete all tasks
    @Query("DELETE FROM task")
    public abstract void deleteAll();

    //select task with employee ID
    @Query("SELECT * FROM task WHERE idEmployee= :employeeId")
    public abstract LiveData<List<TaskEntity>> getTasksOfEmployee(long employeeId);
}
