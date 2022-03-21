package database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.os.AsyncTask;

import java.util.concurrent.Executors;

import database.dao.EmployeeDao;
import database.dao.TaskDao;



public abstract class AppDataBase extends RoomDatabase {

    private static final String TAG ="AppDatabase";

    private static AppDataBase instance;

    private static final String DATABASE_NAME ="bank-database";

    public abstract EmployeeDao employeeDao();

    public abstract TaskDao taskDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated= new MutableLiveData<>();

    public static  AppDataBase getInstance(final Context context){
        if(instance == null){
            synchronized (AppDataBase.class){
                if(instance==null){
                    instance= buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDataBase buildDatabase(final Context appContext){
        Log.i(TAG, "Database will be initilized on the mobile");
        return Room.databaseBuilder(appContext, AppDataBase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(()-> {
                    AppDataBase dataBase = AppDataBase.getInstance(appContext);
                    initializeDemoData(dataBase);
                    dataBase.setDatabaseCreated();

                });
            }
        }).build();
    }


    public static void initializeDemoData(final AppDataBase dataBase){
        Executors.newSingleThreadExecutor().execute(() -> {
            dataBase.runInTransaction(()-> {
                Log.i(TAG, "Wipe database.");
                /*
                dataBase.employeeDao().deleteAll();
                dataBase.taskDao().deleteAll();
                */
                DatabaseInitializer.populateDatabase(dataBase);
            });
        });
    }


    private void updateDatabaseCreated(final Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


    public LiveData<Boolean> getDatabaseCreated(){

        return mIsDatabaseCreated;
    }


}
