package database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import baseapp.BaseApp;
import database.async.task.CreateTask;
import database.async.task.DeleteTask;
import database.async.task.UpdateTask;
import database.entity.TaskEntity;
import util.OnAsyncEventListener;

public class TaskRepository {


    private static TaskRepository instance;

    private TaskRepository(){

    }

    public static TaskRepository getInstance(){
        if(instance==null){
            synchronized (TaskRepository.class){
                if(instance==null){
                    instance= new TaskRepository();
                }
            }
        }
        return instance;
    }


    //Get des taches

    public LiveData<TaskEntity> getTask(final Long taskId, Application application){
        return ((BaseApp) application).getDatabase().taskDao().getById(taskId);
    }

    public LiveData<List<TaskEntity>> getTaks(Application application){
        return ((BaseApp) application).getDatabase().taskDao().getAll();
    }

    public LiveData<List<TaskEntity>> getTasksOfEmployee(Application application , long employeeId){
        return ((BaseApp) application).getDatabase().taskDao().getTasksOfEmployee(employeeId);
    }

    //Insertion d'un employée
    public void insert(final TaskEntity task, OnAsyncEventListener callback, Application application){
        new CreateTask(application, callback).execute(task);
    }

    //Delete d'un employée
    public void delete(final TaskEntity task, OnAsyncEventListener callback, Application application){
        new DeleteTask(application, callback).execute(task);
    }

    //Update d'un employée
    public void update(final TaskEntity task, OnAsyncEventListener callback, Application application){
        new UpdateTask(application, callback).execute(task);
    }
}
