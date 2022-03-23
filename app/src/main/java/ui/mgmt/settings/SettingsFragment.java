package ui.mgmt.settings;


import android.app.UiModeManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beaud_devanthery_timetracker.R;
import com.example.beaud_devanthery_timetracker.databinding.FragmentSettingsBinding;

import ui.mgmt.MyAlertDialog;

public class SettingsFragment extends Fragment {
    private Button aboutUs;
    private FragmentSettingsBinding binding;
    private View root;
    private UiModeManager uiModeManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("java of settings");
        uiModeManager = (UiModeManager) getActivity().getSystemService(getActivity().getBaseContext().UI_MODE_SERVICE);
        getActivity().setTheme(R.style.Theme_Dark);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //aboutUs = root.findViewById(R.id.btnAboutUs);
        binding.btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("about us clicked");

                MyAlertDialog ad = new MyAlertDialog(root.getContext(), "about us", "TimeTracker was developped by Nicolas Devanthery & Simon beaud in 2022 Android developpment course in HES-SO. It has been supervised by Dr. Schumacher and his Assistant Yvan Pannatier", "OK");
                ad.aboutUs();
            }
        });


        // Inflate the layout for this fragment
        return root;
    }



}