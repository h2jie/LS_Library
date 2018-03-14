package com.example.hh.salle_library;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private static final String PREFERENCE_NAME = "Reg";
    EditText et_username, et_password;
    Button btn_login;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser()!=null){
            goToMainScreen();

        }
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        et_username = view.findViewById(R.id.et_email_login);
        et_password = view.findViewById(R.id.et_password_login);



        btn_login = view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                goToMainScreen();
                                getActivity().finish();
                            } else {

                                Toast.makeText(getContext(), getString(R.string.usernameOrPassIncorrect), Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }else{
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.enterUsernameAndPass,
                    Toast.LENGTH_LONG).show();
        }




    }


    void goToMainScreen(){
        Intent intent= new Intent(getActivity().getApplicationContext(),MainScreen.class);
        /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
        startActivity(intent);

    }
}
