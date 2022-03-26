package ui.mgmt.createtask;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.beaud_devanthery_timetracker.databinding.FragmentCreatetaskBinding;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import database.async.employee.CreateEmployee;
import database.async.task.CreateTask;
import database.entity.TaskEntity;
import ui.mgmt.LoginActivity;
import util.OnAsyncEventListener;

public class CreateTaskFragment extends Fragment {

    private FragmentCreatetaskBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateTaskViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CreateTaskViewModel.class);

        binding = FragmentCreatetaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the task the user wants to create
                String description = binding.txtDescription.getText().toString();
                String endTime = binding.txtEndTime.getText().toString();
                String startTime = binding.txtStartTime.getText().toString();
                String title = binding.txtTitle.getText().toString();


                ////////////////////////////////////////////////////////////////////
                //// test if the end is not before start?
                ////////////////////////////////////////////////////////////////////


                binding.txtTitle.setText("");
                binding.txtDescription.setText("");
                binding.txtEndTime.setText("");
                binding.txtStartTime.setText("");

                ////////////////////////////////////////////////////////////////////
                //// store in the DB to add
                ////////////////////////////////////////////////////////////////////
                TaskEntity newTask = new TaskEntity();
                newTask.setDescription(description);
                String[] startSplit = startTime.split(":");

                String[] endSplit = endTime.split(":");
                int endHour = Integer.parseInt(endSplit[0]);
                int endMinute = Integer.parseInt(endSplit[1]);
                int startHour = Integer.parseInt(startSplit[0]);
                int startMinute = Integer.parseInt(startSplit[1]);

                newTask.setEndTime(endHour*60 + endMinute);
                newTask.setStartTime(startHour*60 + startMinute);
                newTask.setTaskname(title);
                Date today = Calendar.getInstance().getTime();
                String myDate =String.format("%02d.%02d.%04d", today.getDay() , today.getMonth() , today.getYear()+1900);
                newTask.setDate(myDate);
                newTask.setIdEmployee(LoginActivity.LOGGED_EMPLOYEE.getId());

                new CreateTask(getActivity().getApplication(), new OnAsyncEventListener() {

                    @Override
                    public void onSuccess() {
                        System.out.println("La task a bien été ajouté");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("La task ne s'est pas ajouté");
                    }
                }).execute(newTask);


            }
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}