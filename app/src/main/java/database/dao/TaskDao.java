package database.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import database.entity.Task;

@Dao
public interface TaskDao {
    @Insert
    void insertAllTaskData(Task task);

    @Query("select* from Task")
    List<Task> getAllTaskData();
}
