package ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.beaud_devanthery_timetracker.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.beaud_devanthery_timetracker.R;

import database.entity.TaskEntity;


public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_history, R.id.navigation_Add, R.id.navigation_Profile,R.id.navigation_Settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //set the default page to add task




    }

    public void removePayOnClickHandler(View v) {
        TaskEntity item = (TaskEntity) v.getTag();
        System.out.println(item.getTaskname() + " " + item.getDate());
    }

    public void GoToLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }




}