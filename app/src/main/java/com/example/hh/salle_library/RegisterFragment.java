package com.example.hh.salle_library;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btn_register;
    EditText et_username, et_password1, et_password2, et_email;
    UserSession session;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        et_username = view.findViewById(R.id.et_username_reg);
        et_password1 = view.findViewById(R.id.et_password1_reg);
        et_password2 = view.findViewById(R.id.et_password2_reg);
        et_email = view.findViewById(R.id.et_email_reg);
        btn_register = view.findViewById(R.id.btn_register);

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("Reg",0);

        editor = sharedPreferences.edit();

        btn_register.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        String name = et_username.getText().toString();
        String email = et_email.getText().toString();
        String password1 = et_password1.getText().toString();
        String password2 = et_password2.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.needName),Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.needemail),Toast.LENGTH_SHORT).show();
        }else if (password1.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.needPass1),Toast.LENGTH_SHORT).show();
        }else if(password2.isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.needPass2),Toast.LENGTH_SHORT).show();
        }else if (!password1.equals(password2)){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.pass1IsDifferentPass2),Toast.LENGTH_SHORT).show();
        }else{
            editor.putString("Name",name);
            editor.putString("Email",email);
            editor.putString("Password",password1);
            editor.commit();

            TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.alreadyRegistered),Toast.LENGTH_SHORT).show();
        }

    }


}
