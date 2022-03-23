package ui.mgmt.modifytask;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beaud_devanthery_timetracker.R;
import com.example.beaud_devanthery_timetracker.databinding.ModifyTaskFragmentBinding;

import database.async.employee.UpdateEmployee;
import database.async.task.UpdateTask;
import database.entity.TaskEntity;
import ui.mgmt.history.HistoryFragment;
import util.OnAsyncEventListener;

public class ModifyTask extends Fragment {

    private ModifyTaskViewModel mViewModel;
    private ModifyTaskFragmentBinding binding;

    public static ModifyTask newInstance() {
        return new ModifyTask();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        long id = (Long)arguments.get("idTask");
        String title = (String)arguments.get("title");
        String description = (String)arguments.get("description");
        String date = (String)arguments.get("date");
        int start = (int)arguments.get("start");
        int end = (int)arguments.get("end");
        System.out.println(title);

        binding = ModifyTaskFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.txtTitlemodify.setText(title);
        binding.txtDescriptionmodify.setText(description);
        binding.txtStartTimemodify.setText(String.format("%02d:%02d",start/60,start%60));
        binding.txtEndTimemodify.setText(String.format("%02d:%02d",end/60,end%60));

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.nav_host_fragment_activity_main, HistoryFragment.class,null);
                transaction.commit();
            }
        });


        binding.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //modify in database
                String description = binding.txtDescriptionmodify.getText().toString();
                String endTime = binding.txtEndTimemodify.getText().toString();
                String startTime = binding.txtStartTimemodify.getText().toString();
                String title = binding.txtTitlemodify.getText().toString();

                String[] startSplit = startTime.split(":");
                String[] endSplit = endTime.split(":");
                int endHour = Integer.parseInt(endSplit[0]);
                int endMinute = Integer.parseInt(endSplit[1]);
                int startHour = Integer.parseInt(startSplit[0]);
                int startMinute = Integer.parseInt(startSplit[1]);


                int endInt = endHour*60 + endMinute;
                int startInt = startHour*60 + startMinute;

                TaskEntity task = new TaskEntity(title,description,startInt,endInt,date);
                task.setId(id);




                new UpdateTask(getActivity().getApplication(), new OnAsyncEventListener() {

                    @Override
                    public void onSuccess() {
                        System.out.println("La task a bien été modifiée");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("La task ne s'est pas modifiée");
                    }
                }).execute(task);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.nav_host_fragment_activity_main, HistoryFragment.class,null);
                transaction.commit();
            }
        });

        return root;

    }

}