package ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.service.carrier.CarrierMessagingService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.MailTo;

import com.example.beaud_devanthery_timetracker.R;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import database.AppDataBase;
import database.async.employee.CreateEmployee;
import database.dao.EmployeeDao;
import database.entity.EmployeeEntity;
import util.OnAsyncEventListener;

public class CreateAccountActivity extends AppCompatActivity {


    private static final String TAG = "CreateAccountActivity";
    private Toast toast;

    //Déclaration des variables
    private AppDataBase database;
    private EmployeeDao employeeDao;
    private Button buttonRegister;
    private EditText Name, Firstname, Function, Telnumber, Email, Address, Username, Password, NPA;
    private String Image_url;
    private Boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        database = AppDataBase.getInstance(this.getBaseContext());
        buttonRegister = findViewById(R.id.modifyProfileButton);

        Name = findViewById(R.id.createAccount_lastname);
        Firstname = findViewById(R.id.createAccount_firstname);
        Function = findViewById(R.id.createAccount_function);
        Telnumber = findViewById(R.id.createAccount_phone);
        Email = findViewById(R.id.createAccount_email);
        Address = findViewById(R.id.createAccount_address);
        Username = findViewById(R.id.createAccount_username);
        Password = findViewById(R.id.createAccount_password);
        NPA = findViewById(R.id.createAccount_npa);
    }


    public void Register(View view) {
        String stName = (String) Name.getText().toString();
        String stFirstname = (String) Firstname.getText().toString();
        String stFunction = (String) Function.getText().toString();
        String stTelNumber = (String) Telnumber.getText().toString();
        String stEmail = (String) Email.getText().toString();
        String stAddress = (String) Address.getText().toString();
        String stImage_Url = "nothing for this time";
        String stUsername = (String) Username.getText().toString();

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
        Boolean stIsAdmin = false;
        String stNPA = (String) NPA.getText().toString();


        EmployeeEntity employee = new EmployeeEntity();

        employee.setName(stName);
        employee.setFirstName(stFirstname);
        employee.setFunction(stFunction);
        employee.setTelnumber(stTelNumber);
        employee.setEmail(stEmail);
        employee.setAddress(stAddress);
        employee.setImage_Url(stImage_Url);
        employee.setUsername(stUsername);
        employee.setPassword(encryptedPassword);
        employee.setAdmin(stIsAdmin);
        employee.setNPA(stNPA);

        new CreateEmployee(getApplication(), new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                System.out.println("Le user a bien été ajouté");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Le user ne s'est pas ajouté");
            }
        }).execute(employee);


        Toast.makeText(getApplicationContext(), "New account added to database", Toast.LENGTH_SHORT).show();
        System.out.println("EMPLOYEE ADDED TO DATABASE");
        backLogin();

    }

    public boolean CheckIfEmpty() {
        boolean anyisempty = true;

        if (Name.getText().toString().equals("")) {
            showError(Name, "Can not be empty");
            return true;
        }
        if (Firstname.getText().toString().equals("")) {
            showError(Firstname, "Can not be empty");
            return true;
        }
        if (Function.getText().toString().equals("")) {
            showError(Function, "Can not be empty");
            return true;
        }
        if (Telnumber.getText().toString().equals("")) {
            showError(Telnumber, "Can not be empty");
            return true;
        }

        if (Email.getText().toString().equals("")) {
            showError(Email, "Can not be empty");
            return true;
        }

        if (Address.getText().toString().equals("")) {
            showError(Address, "Can not be empty");
            return true;
        }

        if (Username.getText().toString().equals("")) {
            showError(Username, "Can not be empty");
            return true;
        }
        if (Password.getText().toString().equals("")) {
            showError(Password, "Can not be empty");
            return true;
        }
        if (NPA.getText().toString().equals("")) {
            showError(NPA, "Can not be empty");
            return true;
        } else
            return false;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
    }

    public void backLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}

