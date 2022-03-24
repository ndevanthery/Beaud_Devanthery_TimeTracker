package ui.mgmt.history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import com.example.beaud_devanthery_timetracker.R;
import com.example.beaud_devanthery_timetracker.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.TaskAdapter;
import baseapp.BaseApp;
import database.entity.TaskEntity;
import database.repository.EmployeeRepository;
import database.repository.TaskRepository;
import ui.mgmt.LoginActivity;
import ui.mgmt.MainActivity;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private View root;
    ListView list;
    private TaskAdapter myAdapter;
    ListView simpleList;
    List<TaskEntity> myListOfTasks;

    private TaskRepository repository;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        repository = ((BaseApp)getActivity().getApplication()).getTaskRepository();

        myListOfTasks = new ArrayList<>();

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        list = (ListView)root.findViewById(R.id.myListViewHistory);
        myListOfTasks = new ArrayList<>();
        myAdapter = new TaskAdapter(getActivity().getBaseContext(),R.layout.history_task_fragment,myListOfTasks,inflater,getActivity().getApplication(),root.getContext(),getParentFragmentManager());
        list.setAdapter(myAdapter);


        LiveData<List<TaskEntity>> taks = repository.getTaks(getActivity().getApplication());
        repository.getTaks(getActivity().getApplication()).observe(getActivity(), taskEntities -> {
            if (taskEntities != null) {
                myListOfTasks.clear();


                for(int i=0;i<taskEntities.size();i++)
                {

                    myListOfTasks.add(taskEntities.get(i));
                    list.setAdapter(myAdapter);
                }
            } else {
                System.out.println("NO TASKS MAYDAY");
            }
        });




        return root;
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void removePayOnClickHandler(View v) {
        TaskEntity item = (TaskEntity) v.getTag();
        System.out.println(item.getTaskname() + " " + item.getDate());
    }


}