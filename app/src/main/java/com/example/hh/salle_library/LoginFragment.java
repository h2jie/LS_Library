package com.example.hh.salle_library;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    UserSession userSession;
    private SharedPreferences sharedPreferences;


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

        userSession = new UserSession(getActivity().getApplicationContext());

        /*if (!userSession.CheckLogin()){
            Intent intent= new Intent(getActivity().getApplicationContext(),MainScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/



        userSession = new UserSession(getActivity().getApplicationContext());

        et_username = view.findViewById(R.id.et_username_login);
        et_password = view.findViewById(R.id.et_password_login);

        Toast.makeText(getActivity().getApplicationContext(),
                "User Login Status: " + userSession.userLoggedIn(),
                Toast.LENGTH_LONG).show();

        btn_login = view.findViewById(R.id.btn_login);
        sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        btn_login.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (username.trim().length()>0 && password.trim().length()>0){
            String userName = null;
            String userPassword = null;
            if (sharedPreferences.contains("Name")){
                userName = sharedPreferences.getString("Name","");
            }

            if (sharedPreferences.contains("Password")){
                userPassword = sharedPreferences.getString("Password","");
            }

            try{
                if (userName.equals(userName) && password.equals(userPassword)){
                    userSession.CreateUserLoginSession(userName,userPassword);

                    /*Intent intent= new Intent(getActivity().getApplicationContext(),MainScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);*/
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        goToMainScreen();
                                    } else {

                                        Toast.makeText(getContext(), "No se ha podido conectar",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });



                    goToMainScreen();

                }else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.usernameOrPassIncorrect,
                            Toast.LENGTH_LONG).show();
                }
            }catch (NullPointerException e){
                Toast.makeText(getActivity().getApplicationContext(),R.string.usernameOrPassIncorrect,Toast.LENGTH_LONG).show();
            }


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
