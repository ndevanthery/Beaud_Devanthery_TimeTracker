package ui.mgmt.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.beaud_devanthery_timetracker.databinding.FragmentProfileBinding;

import database.entity.EmployeeEntity;
import ui.mgmt.LoginActivity;
import ui.mgmt.MyAlertDialog;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EmployeeEntity myProfile = LoginActivity.LOGGED_EMPLOYEE;

        binding.lblEmail.setText(myProfile.getEmail());
        binding.lblFirstName.setText(myProfile.getFirstName());
        binding.lblName.setText(myProfile.getName());
        binding.lblNumber.setText(myProfile.getTelnumber());


        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlertDialog ad = new MyAlertDialog(getContext(),"Delete account ? ","are you sure you want to delete this account?","delete");
                ad.deleteAccount(myProfile,getActivity().getApplication());

            }
        });


//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final Button logout = binding.btnLogout;
        //make something on button click

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}