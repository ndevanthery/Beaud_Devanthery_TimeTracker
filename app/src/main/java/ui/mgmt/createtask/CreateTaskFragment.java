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

                System.out.println(title+" | "+ description + " | " + startTime + " | " + endTime );

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