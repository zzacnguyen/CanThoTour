package com.doan3.canthotour.View.Personal.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doan3.canthotour.R;

import static com.doan3.canthotour.View.Main.Content.FragmentService.bottomFragNavBar;


public class FragmentRegister extends Fragment {

    View v;
    EditText etUserName, etPassword, etConfirmPassword, etCountry, etLanguage;
    Button btnReg;
    int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register, container, false);

        etUserName = v.findViewById(R.id.etUserName);
        etPassword = v.findViewById(R.id.etPassword);
        etConfirmPassword = v.findViewById(R.id.etPasswordConfirm);
        etCountry = v.findViewById(R.id.etCountry);
        etLanguage = v.findViewById(R.id.etLanguege);
        btnReg = v.findViewById(R.id.btnRegister);

        

        bottomFragNavBar(3, v, getActivity());

        return v;
    }
}
