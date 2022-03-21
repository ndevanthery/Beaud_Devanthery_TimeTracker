package database.async.task;

import android.app.Application;
import android.os.AsyncTask;

import baseapp.BaseApp;
import database.entity.TaskEntity;
import util.OnAsyncEventListener;

public class DeleteTask extends AsyncTask<TaskEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteTask(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(TaskEntity... taskEntities) {
        try{
            for(TaskEntity task : taskEntities){
                ((BaseApp)application).getDatabase().taskDao().delete(task);
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
