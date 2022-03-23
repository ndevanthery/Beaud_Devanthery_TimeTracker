package ui.mgmt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Delete;

import com.example.beaud_devanthery_timetracker.R;

import database.async.employee.DeleteEmployee;
import database.async.task.CreateTask;
import database.entity.EmployeeEntity;
import database.entity.TaskEntity;
import ui.mgmt.modifytask.ModifyTask;
import util.OnAsyncEventListener;

public class MyAlertDialog {

    private  int ThemeID;
    String dialogTitle, dialogMsg, dialogYesBtn, dialogNoBtn = "Cancel";
    Context context;
    AlertDialog.Builder myAlert;


    public MyAlertDialog(Context context, String title, String msg, String yesMsg) {
        myAlert = new AlertDialog.Builder(context,getThemeID());
        setDialogTitle(title);
        setDialogMsg(msg);
        setDialogYesBtn(yesMsg);
        this.context = context;
    }

    public void killProgram(){
        myAlert.setPositiveButton(dialogYesBtn, (dialog, which) -> {
            dialog.dismiss();
            System.out.println("------------------------");
            System.out.println("KILL PROCESS DONE");
            System.out.println(" by the User");
            System.out.println("------------------------");
            //if you want to kill app . from other then your main avtivity.(Launcher)
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            //if you want to finish just current activity
            // LoginActivity.this.finish();
        });
        myAlert.setNegativeButton(dialogNoBtn, (dialog, which) -> dialog.dismiss());
        myAlert.show();
    }

    public void backToLoginPage(){
        myAlert.setPositiveButton(dialogYesBtn, (dialog, which) -> {
            dialog.dismiss();
            SharedPreferences.Editor editor = context.getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
            editor.remove(MainActivity.PREFS_USER);
            editor.apply();
            LoginActivity.LOGGED_EMPLOYEE=null;
            Intent intent= new  Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        });
        myAlert.setNegativeButton(dialogNoBtn, (dialog, which) -> dialog.dismiss());
        myAlert.show();
    }

    public void aboutUs(){
        myAlert.setPositiveButton(dialogYesBtn, (dialog, which) -> {
                    dialog.dismiss();
                });
        myAlert.show();
    }

    public void details(FragmentManager fragmentManager, TaskEntity taskEntity){
        myAlert.setPositiveButton(dialogYesBtn, (dialog, which) -> {
            dialog.dismiss();
            SharedPreferences.Editor editor = context.getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
            editor.remove(MainActivity.PREFS_USER);
            editor.apply();
            Bundle arguments = new Bundle();
            arguments.putString("title",taskEntity.getTaskname());
            arguments.putString("description",taskEntity.getDescription());
            arguments.putString("date",taskEntity.getDate());
            arguments.putInt("start",taskEntity.getStartTime());
            arguments.putInt("end",taskEntity.getEndTime());
            arguments.putLong( "idTask",taskEntity.getId());


            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setReorderingAllowed(true);

// Replace whatever is in the fragment_container view with this fragment
            transaction.replace(R.id.nav_host_fragment_activity_main, ModifyTask.class,arguments);

// Commit the transaction
            transaction.commit();

        });
        myAlert.setNegativeButton(dialogNoBtn, (dialog, which) -> dialog.dismiss());
        myAlert.show();
    }

    public void deleteAccount(EmployeeEntity employee,Application application){
        myAlert.setPositiveButton(dialogYesBtn, (dialog, which) -> {
            dialog.dismiss();
            SharedPreferences.Editor editor = context.getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
            editor.remove(MainActivity.PREFS_USER);
            editor.apply();

            new DeleteEmployee(application, new OnAsyncEventListener() {

                @Override
                public void onSuccess() {
                    System.out.println("L'employé a été supprimé");
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("L'employé n'a pas été supprimé");
                }
            }).execute(employee);

            Intent intent= new  Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        });
        myAlert.setNegativeButton(dialogNoBtn, (dialog, which) -> dialog.dismiss());
        myAlert.show();
    }


    public int getThemeID() {
        return ThemeID;
    }

    public void setThemeID(int themeID) {
        ThemeID = themeID;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public String getDialogMsg() {
        return dialogMsg;
    }

    public void setDialogTitle(String title) {
        myAlert.setTitle(title);
    }

    public void setDialogMsg(String msg) {
        myAlert.setMessage(msg);
    }

    public AlertDialog.Builder getMyAlert() {
        return myAlert;
    }

    public String getDialogYesBtn() {
        return dialogYesBtn;
    }

    public void setDialogYesBtn(String dialogYesBtn) {
        this.dialogYesBtn = dialogYesBtn;
    }

    public String getDialogNoBtn() {
        return dialogNoBtn;
    }

    public void setDialogNoBtn(String dialogNoBtn) {
        this.dialogNoBtn = dialogNoBtn;
    }
}

