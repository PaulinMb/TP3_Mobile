package com.example.partiemysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<Drawable> drawsPays;
    ArrayList<String> listNomsPays;

    Context context;
    Intent intent;
    SharedPreferences sharedPreferences;
    String SHARED_PREF_NAME ="user_pref";
    SharedPreferences.Editor sharedPrefEditor;
    protected String name,surname,email,password,pays;

    protected boolean isLoggedIn(){
        return sharedPreferences.getBoolean("login",false);
    }
    protected void logout(){
        sharedPrefEditor.putBoolean("login",false);
        sharedPrefEditor.apply();
        sharedPrefEditor.commit();
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void setArraylistsPays(){
        drawsPays = new ArrayList<Drawable>();
        listNomsPays = new ArrayList();

        TypedArray tAPays = getResources().obtainTypedArray(R.array.pays);
        for (int i =0 ;i < tAPays.length(); i++) {

            char[] ch = new char[100];
            String nomPays = tAPays.getString(i).replaceAll("_"," ");
            nomPays.getChars(13, nomPays.length()-4, ch,0);

            listNomsPays.add(String.valueOf(ch));
            drawsPays.add(tAPays.getDrawable(i));
        }
    }
}