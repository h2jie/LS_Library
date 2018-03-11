package com.example.hh.salle_library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by HangjieHuang on 2018/3/11.
 */

public class UserSession {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;
    //Modo de shared preferences
    private int PRIVATE_MODE = 0;
    //Nombre fichero de shared preferences
    private static final String PREFERENCES_NAME ="Reg";

    //Key de shared preferences
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    private static final String KEY_NAME="NAME";
    private static final String KEY_EMAIL = "Email";
    public static final String KEY_PASSWORD ="Password";

    public UserSession(Context _context) {
        this._context = _context;
        sharedPreferences = _context.getSharedPreferences(PREFERENCES_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void CreateUserLoginSession(String userName, String userPassword){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, userName);
        editor.putString(KEY_EMAIL,userPassword);

        editor.commit();
    }

    //Check para login
    public boolean userLoggedIn(){
        return sharedPreferences.getBoolean(IS_USER_LOGIN,false);
    }


    //Check estado de login
    public boolean CheckLogin(){
        if (!this.userLoggedIn()){
            Intent intent = new Intent(_context, LoginFragment.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
            return true;
        }
        return false;
    }

    //Get data de session
    private HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        user.put(KEY_EMAIL,sharedPreferences.getString(KEY_NAME,null));

        return user;
    }

    //Eliminar session details
    public void LogOut(){

        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, AuthenticationActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(intent);
    }
}
