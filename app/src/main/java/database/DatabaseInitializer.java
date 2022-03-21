package database;


import android.util.Log;

import database.entity.EmployeeEntity;
import database.entity.TaskEntity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDataBase db){
        Log.i(TAG, "inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }


    private static void addEmployee(final AppDataBase db,
                                    final String Name,
                                    final String FirstName,
                                    final String Function,
                                    final String Telnumber,
                                    final String Email,
                                    final String Address,
                                    final String NPA,
                                    final String Image_Url,
                                    final String Username,
                                    final String Password,
                                    final Boolean isAdmin){

        EmployeeEntity employee = new EmployeeEntity(
                Name,
                FirstName,
                Function,
                Telnumber,
                Email,
                Address,
                NPA,
                Image_Url,
                Username,
                Password,
                isAdmin);

        db.employeeDao().insert(employee);


    }


    private static void addTask(final AppDataBase db,
                                final String Taskname,
                                final String Description,
                                final int StartTime,
                                final int EndTime,
                                final String Date){
        TaskEntity task = new TaskEntity(
                Taskname,
                Description,
                StartTime,
                EndTime,
                Date);

        db.taskDao().insert(task);
    }

    private static void populateWithTestData(AppDataBase db){
        addEmployee(db,
                "Beaud",
                "Simon",
                "Manager",
                "+41765738650",
                "contact@timetracker.com",
                "Rue du Bourg",
                "CH-1700",
                "www.google.com",
                "BeaudS",
                "1234",
                false);
        System.out.println("USER TEST AJOUTE");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addTask(db,
                "CallClient",
                "Call client to present the news about the company",
                1230,
                1430,
                "25.03.2022");

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final AppDataBase dataBase;

        private PopulateDbAsync(AppDataBase dataBase) {
            this.dataBase = dataBase;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(dataBase);
            return null;
        }
    }
}
