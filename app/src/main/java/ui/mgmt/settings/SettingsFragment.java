package ui.mgmt.settings;


import android.app.ActionBar;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.beaud_devanthery_timetracker.databinding.FragmentSettingsBinding;

import ui.mgmt.MyAlertDialog;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //link with the xml file view
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //when button "about us" is clicked
        binding.btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //opens the about us alert dialog
                MyAlertDialog ad = new MyAlertDialog(root.getContext(), "about us", "TimeTracker was developped by Nicolas Devanthery & Simon beaud in 2022 Android developpment course in HES-SO. It has been supervised by Dr. Schumacher and his Assistant Yvan Pannatier", "OK");
                ad.aboutUs();
            }
        });

        //when switch "dark mode" is touched
        binding.darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //if the switch was checked
                if(binding.darkModeSwitch.isChecked())
                {
                    //put the app in dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    //else, put the default mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }

            }
        });
        return root;
    }


}