package ui.mgmt.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.beaud_devanthery_timetracker.R;
import com.example.beaud_devanthery_timetracker.databinding.FragmentProfileBinding;

import database.entity.EmployeeEntity;
import ui.mgmt.LoginActivity;
import ui.mgmt.MyAlertDialog;
import ui.mgmt.modifyemployee.ModifyEmployee;
import ui.mgmt.modifytask.ModifyTask;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EmployeeEntity myProfile = LoginActivity.LOGGED_EMPLOYEE;

        binding.lblEmail.setText(myProfile.getEmail());
        binding.lblFirstName.setText(myProfile.getFirstName());
        binding.lblName.setText(myProfile.getName());
        binding.lblNumber.setText(myProfile.getTelnumber());

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlertDialog ad = new MyAlertDialog(getContext(),"Logout ? ","are you sure you want to log out","log out");
                ad.backToLoginPage();

            }
        });

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                transaction.setReorderingAllowed(true);

                Bundle args = new Bundle();
                args.putLong("id", myProfile.getId());
                args.putString("username",myProfile.getUsername());
                args.putString("password",myProfile.getPassword());
                args.putString("email",myProfile.getEmail());
                args.putString("firstname",myProfile.getFirstName());
                args.putString("name",myProfile.getName());
                args.putString("number",myProfile.getTelnumber());
                args.putString("address",myProfile.getAddress());
                args.putString("function",myProfile.getFunction());
                args.putString("npa",myProfile.getNPA());
                args.putBoolean("admin",myProfile.getAdmin());

// Replace whatever is in the fragment_container view with this fragment
                transaction.replace(R.id.nav_host_fragment_activity_main, ModifyEmployee.class,args);

// Commit the transaction
                transaction.commit();
            }
        });


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