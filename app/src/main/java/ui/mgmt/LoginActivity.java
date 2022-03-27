package ui.mgmt;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beaud_devanthery_timetracker.R;

import java.security.NoSuchAlgorithmException;

import baseapp.BaseApp;
import database.AppDataBase;
import database.entity.EmployeeEntity;
import database.repository.EmployeeRepository;
import java.security.MessageDigest;


public class LoginActivity extends AppCompatActivity {

    public static EmployeeEntity LOGGED_EMPLOYEE;

    private EditText Username, Password;

    private EmployeeRepository repository;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //link to the db
        repository = ((BaseApp)getApplication()).getEmployeeRepository();

        Username =  findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);


    }

    public void GoToRegister(View view){
        startActivity(new Intent(this, CreateAccountActivity.class));
    }


    public void Login(View view) {


        Username.setError(null);
        Password.setError(null);

        String stUsername = Username.getText().toString();
        //encrypt the password to compare to the one in the db
        String encryptedPassword = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(Password.getText().toString().getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = s.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        boolean cancel = false;
        View focusView = null;

        //Check if the username is valid
        if(TextUtils.isEmpty(stUsername)){
            Username.setError(getString(R.string.error_username_required));
            focusView = Username;
            cancel=true;
        }else if(!isUsernameValid(stUsername)){
            Username.setError(getString(R.string.error_invalid_username));
            focusView = Username;
            cancel=true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            String finalEncryptedPassword = encryptedPassword;
            repository.getEmployee(stUsername, getApplication()).observe(LoginActivity.this, employeeEntity -> {
                if (employeeEntity != null) {
                    //if login is correct
                    if (employeeEntity.getPassword().equals(finalEncryptedPassword)) {

                        //store the employee that logged
                        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
                        editor.putString(MainActivity.PREFS_USER, employeeEntity.getUsername());
                        editor.apply();
                        LOGGED_EMPLOYEE = employeeEntity;

                        //go to mainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        Username.setText("");
                        Password.setText("");
                    } else {
                        Password.setError(getString(R.string.error_incorrect_password));
                        Password.requestFocus();
                        Password.setText("");
                    }
                } else {
                    Username.setError(getString(R.string.error_invalid_username));
                    Username.requestFocus();
                    Username.setText("");
                }
            });
        }
    }

    private boolean isUsernameValid(String username){
        return true;
    }

    private void showError(EditText input, String s){
        input.setError(s);
    }
}