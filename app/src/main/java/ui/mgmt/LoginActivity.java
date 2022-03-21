package ui.mgmt;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.beaud_devanthery_timetracker.R;

import java.util.List;

import baseapp.BaseApp;
import database.AppDataBase;
import database.entity.EmployeeEntity;
import database.repository.EmployeeRepository;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;
    private Button buttonLogin;
    //private EditText Username, Password;

    //private EmployeeEntity employeeEntity;

    private EmployeeRepository repository;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //repository = ((BaseApp)(this.getApplication())).getEmployeeRepository();
        BaseApp app = ((BaseApp)(this.getApplication()));

        usernameView = findViewById(R.id.editTextTextEmailAddress);
        passwordView = findViewById(R.id.editTextTextPassword);
    }

    public void GoToRegister(View view){
        startActivity(new Intent(this, CreateAccountActivity.class));
    }


    public void Login(View view) {


        usernameView.setError(null);
        passwordView.setError(null);


        buttonLogin= findViewById(R.id.btnLogin);

        String stUsername = usernameView.getText().toString();
        String stPassword = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        //Check if the password is valid
        if(!TextUtils.isEmpty(stPassword) && !isPasswordValid(stPassword)){
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.setText("");
            focusView = passwordView;
            cancel=true;
        }

        //Check if the username is valid
        if(TextUtils.isEmpty(stUsername)){
            usernameView.setError(getString(R.string.error_username_required));
            focusView = usernameView;
            cancel=true;
        }else if(!isUsernameValid(stUsername)){
            usernameView.setError(getString(R.string.error_invalid_username));
            focusView = usernameView;
            cancel=true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            repository.getEmployee(stUsername, getApplication()).observe(LoginActivity.this, employeeEntity -> {
                if (employeeEntity != null) {
                    if (employeeEntity.getPassword().equals(stPassword)) {
                        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
                        editor.putString(MainActivity.PREFS_USER, employeeEntity.getUsername());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        usernameView.setText("");
                        passwordView.setText("");
                    } else {
                        passwordView.setError(getString(R.string.error_incorrect_password));
                        passwordView.requestFocus();
                        passwordView.setText("");
                    }
                } else {
                    usernameView.setError(getString(R.string.error_invalid_username));
                    usernameView.requestFocus();
                    passwordView.setText("");
                }
            });
        }
    }

    private boolean isUsernameValid(String username){
        return true;
    }

    private boolean isPasswordValid(String password){
        return password.length()>=4;
    }

    private void showError(EditText input, String s){
        input.setError(s);
    }
}