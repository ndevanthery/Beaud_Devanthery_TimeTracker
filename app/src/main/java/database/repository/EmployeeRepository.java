package database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import baseapp.BaseApp;
import database.async.employee.CreateEmployee;
import database.async.employee.DeleteEmployee;
import database.async.employee.UpdateEmployee;
import database.entity.EmployeeEntity;
import util.OnAsyncEventListener;

public class EmployeeRepository {

    private static EmployeeRepository instance;

    private EmployeeRepository(){

    }

    public static EmployeeRepository getInstance(){
        if(instance==null){
            synchronized (EmployeeRepository.class){
                if(instance==null){
                    instance= new EmployeeRepository();
                }
            }
        }
        return instance;
    }


    //Get des employées
    public LiveData<EmployeeEntity> getEmployee(final String employeeId, Application application){
        return ((BaseApp) application).getDatabase().employeeDao().getById(employeeId);
    }

    public LiveData<List<EmployeeEntity>> getEmployees(Application application){
        return ((BaseApp) application).getDatabase().employeeDao().getAll();
    }

    //Insertion d'un employée
    public void insert(final EmployeeEntity employee, OnAsyncEventListener callback, Application application){
        new CreateEmployee(application, callback).execute(employee);
    }

    //Delete d'un employée
    public void delete(final EmployeeEntity employee, OnAsyncEventListener callback, Application application){
        new DeleteEmployee(application, callback).execute(employee);
    }

    //Update d'un employée
    public void update(final EmployeeEntity employee, OnAsyncEventListener callback, Application application){
        new UpdateEmployee(application, callback).execute(employee);
    }


}
