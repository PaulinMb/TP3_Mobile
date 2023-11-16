package com.example.partiemysql;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends MainActivity {
    TextView nameTv;
    TextView emailTv;
    TextView surnameTv;
    TextView paysTv;
    Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Home");
        context = this;
        init();
        setContentView(R.layout.activity_home);

        //link views
        getViews();
    }

    private void getViews() {
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("nom",""));
        surnameTv = findViewById(R.id.surnameTv);
        nameTv.setText(sharedPreferences.getString("prenom",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("courriel",""));
        logoutbtn = findViewById(R.id.logoutBtn);
        paysTv = findViewById(R.id.paysTv);
        paysTv.append(sharedPreferences.getString("pays","")+"");

        //make logout
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect back to login page
                logout();
                intent = new Intent(context, LoginActivity.class);
                //remove all previous stack activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}
