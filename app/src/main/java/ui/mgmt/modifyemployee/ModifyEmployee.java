package ui.mgmt.modifyemployee;

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
import com.example.beaud_devanthery_timetracker.databinding.FragmentProfileBinding;
import com.example.beaud_devanthery_timetracker.databinding.ModifyEmployeeFragmentBinding;

import database.async.employee.UpdateEmployee;
import database.entity.EmployeeEntity;
import ui.mgmt.LoginActivity;
import ui.mgmt.history.HistoryFragment;
import ui.mgmt.profile.ProfileFragment;
import util.OnAsyncEventListener;

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
        Bundle arguments = getArguments();
        long id = (long)arguments.get("id");
        String username = (String)arguments.get("username");
        String password = (String)arguments.get("password");
        String email = (String)arguments.get("email");
        String firstname = (String)arguments.get("firstname");
        String name = (String)arguments.get("name");
        String number = (String)arguments.get("number");
        String address = (String)arguments.get("address");
        String function = (String)arguments.get("function");
        String npa = (String)arguments.get("npa");
        boolean admin = (boolean)arguments.get("admin");

        binding.createAccountUsername.setText(username);
        binding.createAccountPassword.setText(password);
        binding.createAccountEmail.setText(email);
        binding.createAccountFirstname.setText(firstname);
        binding.createAccountLastname.setText(name);
        binding.createAccountPhone.setText(number);
        binding.createAccountAddress.setText(address);
        binding.createAccountFunction.setText(function);
        binding.createAccountNpa.setText(npa);


        binding.modifyProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get modified employee

                EmployeeEntity employee = new EmployeeEntity();
                employee.setId(id);
                employee.setUsername(binding.createAccountUsername.getText().toString());
                employee.setPassword(binding.createAccountPassword.getText().toString());
                employee.setEmail(binding.createAccountEmail.getText().toString());
                employee.setFirstName(binding.createAccountFirstname.getText().toString());
                employee.setName(binding.createAccountLastname.getText().toString());
                employee.setTelnumber(binding.createAccountPhone.getText().toString());
                employee.setAddress(binding.createAccountAddress.getText().toString());
                employee.setFunction(binding.createAccountFunction.getText().toString());
                employee.setNPA(binding.createAccountNpa.getText().toString());
                employee.setAdmin(admin);
                LoginActivity.LOGGED_EMPLOYEE = employee;

                //modify in db
                new UpdateEmployee(getActivity().getApplication(), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        System.out.println("employee was updated with success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("employee update failed");

                    }
                }).execute(employee);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.nav_host_fragment_activity_main, ProfileFragment.class,null);
                transaction.commit();
            }
        });

        binding.modifyProfileBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.nav_host_fragment_activity_main, ProfileFragment.class,null);
                transaction.commit();
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