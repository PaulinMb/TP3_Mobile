package com.example.partiemysql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.partiemysql.util.SpinerPaysAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignupActivity extends MainActivity {
    EditText nameEt,surnameEt,emailEt,passwordEt;
    Spinner paysSp;
    Button signupBtn;
    TextView LoginNowTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        init();
        setContentView(R.layout.activity_signup);
        getViews();
        //Choix des pays
        setSpinnerPays();
    }

    public void getViews() {
        nameEt = findViewById(R.id.nameEt);
        surnameEt = findViewById(R.id.surnameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        paysSp = findViewById(R.id.spinnerPays);
        signupBtn = findViewById(R.id.SignupBtn);
        LoginNowTv = findViewById(R.id.LoginNowTv);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupValidation();
            }
        });
        LoginNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void signupValidation() {
        name = nameEt.getText().toString();
        surname = surnameEt.getText().toString();
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        pays = listNomsPays.get(paysSp.getSelectedItemPosition());

        if (name.length()<3){
            Toast.makeText(context,"Name at least 3 characters.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (surname.length()<3){
            Toast.makeText(context,"Surname at least 3 characters.",Toast.LENGTH_SHORT).show();
            return;
        }
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

        if (pays.length()<3){
            Toast.makeText(context,"Pays name at least 3 characters.",Toast.LENGTH_SHORT).show();
            return;
        }

        //all inputs are validated now perform login request
        RequestParams params = new RequestParams();
        params.add("type","signup");
        params.add("nom",name);
        params.add("prenom",surname);
        params.add("courriel",email);
        params.add("motdepasse",password);
        params.add("pays",pays);

        WebReq.post(context, "http://127.0.0.1/androidApp/api.php", params, new ResponseHandler());
    }
    /***
     * Set le spinner de pays
     */
    private void setSpinnerPays(){
        setArraylistsPays();
        SpinerPaysAdapter imagesAd = new SpinerPaysAdapter(this, drawsPays, listNomsPays);
        paysSp.setAdapter(imagesAd);
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
                    sharedPrefEditor.putString("user_id",user.getString("user_id"));
                    sharedPrefEditor.putString("nom",user.getString("nom"));
                    sharedPrefEditor.putString("prenom",user.getString("prenom"));
                    sharedPrefEditor.putString("courriel",user.getString("courriel"));
                    sharedPrefEditor.putString("motdepasse",user.getString("motdepasse"));
                    sharedPrefEditor.putString("pays",user.getString("pays"));
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();

                    //Move to home activity
                    intent = new Intent(context,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
