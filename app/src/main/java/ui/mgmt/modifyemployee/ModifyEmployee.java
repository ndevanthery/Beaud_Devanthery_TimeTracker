package ui.mgmt.modifyemployee;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beaud_devanthery_timetracker.R;
import com.example.beaud_devanthery_timetracker.databinding.FragmentProfileBinding;
import com.example.beaud_devanthery_timetracker.databinding.ModifyEmployeeFragmentBinding;

public class ModifyEmployee extends Fragment {

    private ModifyEmployeeViewModel mViewModel;
    private ModifyEmployeeFragmentBinding binding;


    public static ModifyEmployee newInstance() {
        return new ModifyEmployee();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = ModifyEmployeeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        return root;
    }


}