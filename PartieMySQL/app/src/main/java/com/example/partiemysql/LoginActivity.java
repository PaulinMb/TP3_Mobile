package com.example.partiemysql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends MainActivity {
    EditText emailEt,passwordEt;
    Button loginBtn;
    TextView signupNowTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Login");
        context=this;
        setContentView(R.layout.activity_login);
        init();
        getViews();
    }

    private void getViews() {
        emailEt = findViewById(R.id.emailEt);
        signupNowTv = findViewById(R.id.signupNowTv);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidation();
            }
        });
        signupNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginValidation() {
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();

        if (email.length()==0){
            Toast.makeText(context,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmailValid(email)==false){
            Toast.makeText(context,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length()<5){
            Toast.makeText(context,"Minimum password length should be 5 characters.",Toast.LENGTH_SHORT).show();
            return;
        }

        //all inputs are validated now perform login request
        RequestParams params = new RequestParams();
        params.add("type","login");
        params.add("courriel",email);
        params.add("motdepasse",password);
        Log.d("Login Parameters",params+"");
        WebReq.post(context, "http://127.0.0.1/androidApp/api.php", params, new ResponseHandler());
    }

    private class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.d("response ",response.toString()+" ");
            try {
                if (response.getBoolean("error")){
                    // failed to login
                    Toast.makeText(context,response.getString("message"),Toast.LENGTH_SHORT).show();
                }else{
                    // successfully logged in
                    JSONObject user = response.getJSONObject("user");
                    //save login values
                    sharedPrefEditor.putBoolean("login",true);
                    sharedPrefEditor.putString("user_id",user.getString("id"));
                    sharedPrefEditor.putString("nom",user.getString("nom"));
                    sharedPrefEditor.putString("prenom",user.getString("prenom"));
                    sharedPrefEditor.putString("courriel",user.getString("courriel"));
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();

                    //Move to home activity
                    intent = new Intent(context,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }
}
