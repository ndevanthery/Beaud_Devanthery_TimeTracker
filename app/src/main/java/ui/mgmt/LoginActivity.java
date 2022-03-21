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
    private AppDataBase database;

    private EmployeeRepository repository;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = AppDataBase.getInstance(this.getBaseContext());



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



        buttonLogin= findViewById(R.id.btnLogin);

        String stUsername = Username.getText().toString();
        String stPassword = Password.getText().toString();

        boolean cancel = false;
        View focusView = null;


        //Check if the password is valid
        if(!TextUtils.isEmpty(stPassword) && !isPasswordValid(stPassword)){
            Password.setError(getString(R.string.error_invalid_password));
            Password.setText("");
            focusView = Password;
            cancel=true;
        }

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
//            progressBar.setVisibility(View.VISIBLE);
            LiveData<EmployeeEntity> myData = repository.getEmployee(stUsername,getApplication());
            repository.getEmployee(stUsername, getApplication()).observe(LoginActivity.this, employeeEntity -> {
                if (employeeEntity != null) {
                    if (employeeEntity.equals(stPassword)) {
                        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
                        editor.putString(MainActivity.PREFS_USER, employeeEntity.getUsername());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Username.setText("");
                    } else {
                        Password.setError(getString(R.string.error_incorrect_password));
                        Password.requestFocus();
                        Password.setText("");
                    }
//                    progressBar.setVisibility(View.GONE);
                } else {
                    Username.setError(getString(R.string.error_invalid_username));
                    Username.requestFocus();
                    Username.setText("");
//                    progressBar.setVisibility(View.GONE);
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