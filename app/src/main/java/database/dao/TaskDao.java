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
    //Insertion d'une tache
    @Insert
    public abstract long insert (TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<TaskEntity> tasks);

    //Update d'une tache
    @Update
    public abstract void update(TaskEntity task);

    //Suppression d'une tache
    @Delete
    public abstract void delete(TaskEntity task);



    //Select sur une tache
    @Query("SELECT * FROM task WHERE id = :id")
    public abstract LiveData<TaskEntity> getById(Long id);

    @Query("SELECT * FROM task")
    public abstract LiveData<List<TaskEntity>> getAll();

    //delete all
    @Query("DELETE FROM task")
    public abstract void deleteAll();
}
