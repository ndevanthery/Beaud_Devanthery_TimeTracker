package database.async.employee;

import android.app.Application;
import android.os.AsyncTask;

import database.entity.EmployeeEntity;
import util.OnAsyncEventListener;
import baseapp.BaseApp;


public class CreateEmployee extends AsyncTask<EmployeeEntity, Void, Void> {

    private final Application application;
    private final OnAsyncEventListener callback;
    private Exception exception;

    public CreateEmployee(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }



    @Override
    protected Void doInBackground(EmployeeEntity... employeeEntities) {
        try{
            for(EmployeeEntity employee : employeeEntities){
                ((BaseApp)application).getDatabase().employeeDao().insert(employee);
            }
        }catch(Exception e){
            exception = e;
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid){
        if(callback !=null){
            if(exception == null){
                callback.onSuccess();
            }else{
                callback.onFailure(exception);
            }
        }
    }
}
