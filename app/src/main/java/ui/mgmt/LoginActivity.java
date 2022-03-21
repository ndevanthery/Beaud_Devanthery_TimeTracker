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

import com.example.beaud_devanthery_timetracker.R;

import baseapp.BaseApp;
import database.AppDataBase;
import database.entity.EmployeeEntity;
import database.repository.EmployeeRepository;


public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView usernameView;
    private AutoCompleteTextView passwordView;
    private Button buttonLogin;
    private EditText Username, Password;
    private ProgressBar progressBar;

    //private EmployeeEntity employeeEntity;

    private EmployeeRepository repository;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = ((BaseApp)getApplication()).getEmployeeRepository();

    }

    public void GoToRegister(View view){
        startActivity(new Intent(this, CreateAccountActivity.class));
    }


    public void Login(View view) {


        usernameView.setError(null);
        passwordView.setError(null);


        Username =  findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        buttonLogin= findViewById(R.id.btnLogin);

        String stUsername = Username.getText().toString();
        String stPassword = Password.getText().toString();

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
            progressBar.setVisibility(View.VISIBLE);
            repository.getEmployee(stUsername, getApplication()).observe(LoginActivity.this, employeeEntity -> {
                if (employeeEntity != null) {
                    if (employeeEntity.equals(stPassword)) {
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
                    progressBar.setVisibility(View.GONE);
                } else {
                    usernameView.setError(getString(R.string.error_invalid_username));
                    usernameView.requestFocus();
                    passwordView.setText("");
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private boolean isUsernameValid(String username){
        return true;
    }

    private boolean isPasswordValid(String password){
        return password.length()>4;
    }

    private void showError(EditText input, String s){
        input.setError(s);
    }
}