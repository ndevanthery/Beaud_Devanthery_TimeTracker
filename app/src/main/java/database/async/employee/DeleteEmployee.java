package database.async.employee;

import android.app.Application;
import android.os.AsyncTask;

import baseapp.BaseApp;
import database.entity.EmployeeEntity;
import util.OnAsyncEventListener;

public class DeleteEmployee extends AsyncTask<EmployeeEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteEmployee(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(EmployeeEntity... employeeEntities) {
        try{
            for(EmployeeEntity employee : employeeEntities){
                ((BaseApp)application).getDatabase().employeeDao().delete(employee);
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
