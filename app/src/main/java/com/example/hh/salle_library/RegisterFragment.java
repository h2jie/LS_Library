package com.example.hh.salle_library;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    Button btn_register;
    EditText et_username, et_password1, et_password2, et_email;
    TextView usernmae;
    public RegisterFragment() {
        // Required empty public constructor
    }


    /**
     * Inicializamos los componentes que hemos utilizado en este view
     *
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();

        et_username = view.findViewById(R.id.et_username_reg);
        et_password1 = view.findViewById(R.id.et_password1_reg);
        et_password2 = view.findViewById(R.id.et_password2_reg);
        et_email = view.findViewById(R.id.et_email_reg);
        btn_register = view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Cuando el usuario click el boton Register, primero verificamos la contraseña
     * que ha introducido el usuario cumple nuestras condiciones, si no cumple, muestra
     * un mensaje de error, si cumple pasamos todos los datos al firebase para registrar
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        final String name = et_username.getText().toString();
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
        }else if (!password1.equals(password2)){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.pass1IsDifferentPass2),Toast.LENGTH_SHORT).show();
        }else if (!email.contains("@") || !email.contains(".")){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.invalidEmail),Toast.LENGTH_SHORT).show();
        }else if (password1.length()<8){
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.needsMore8Characters),Toast.LENGTH_SHORT).show();
        }else{


            mAuth.createUserWithEmailAndPassword(email, password2)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profileUpdates);
                                Intent intent = new Intent(getContext(),MainScreen.class);
                                startActivity(intent);
                                getActivity().finish();

                            } else {
                                Toast.makeText(getContext(), getString(R.string.couldNotRegister),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();

        }

    }


}
