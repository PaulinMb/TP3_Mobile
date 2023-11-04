package com.example.sqlite_ormlite.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.model.User;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

public class JeuActivity extends MainActivity {
    private Intent intent;

    private EditText txtNumber;
    private Button btnCompare;
    private TextView lblResult;
    private ProgressBar pgbScore;
    private TextView lblHistory;

    private int searchedValue;
    private int score;

    private User joueur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        getSupportActionBar().setTitle("TP3-BD : Jeu");

        //Set les UI
        txtNumber = (EditText) findViewById( R.id.txtNumber );
        btnCompare = (Button) findViewById( R.id.btnCompare );
        lblResult = (TextView) findViewById( R.id.lblResult );
        pgbScore = (ProgressBar) findViewById( R.id.pgbScore );
        lblHistory = (TextView) findViewById( R.id.lblHistory );

        btnCompare.setOnClickListener( btnCompareListener );

        //Initalisation du jeu
        intent = getIntent();
        initialiserJoueur();
        init();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        double price = 1_000_000.01;
        Log.i( "DEBUG", formatter.format( price ) );

        DateFormat dataFormatter = DateFormat.getDateTimeInstance();
        Log.i( "DEBUG", dataFormatter.format( new Date() ) );
    }

    private void initialiserJoueur() {
        joueur = new User();

        int idEx = intent.getIntExtra("id",0);
        String[] str = intent.getStringArrayExtra("connect");
        joueur.setIdUser(idEx);
        joueur.setFirstName(str[0]);
        joueur.setLastName(str[1]);
        joueur.setEmail(str[2]);
        joueur.setMotdepasse(str[3]);
        joueur.setPays(str[4]);
        Log.i( "Joueur: ", joueur.toString()  );
    }

    private void init() {
        score = 0;
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i( "DEBUG", "Searched value : " + searchedValue );

        txtNumber.setText( "" );
        pgbScore.setProgress( score );
        lblResult.setText( "" );
        lblHistory.setText( "" );

        txtNumber.requestFocus();
    }

    private void congratulations() {
        String fel = getResources().getString(R.string.strCongratulations);
        SpannableString gras = new SpannableString(fel);
        gras.setSpan(new BackgroundColorSpan(Color.MAGENTA),0, gras.length(), 0);
        lblResult.setText(gras);

        AlertDialog retryAlert = new AlertDialog.Builder( this ).create();
        retryAlert.setTitle( R.string.titre_jeu );
        retryAlert.setMessage( getString(R.string.strMessage, score ) );

        retryAlert.setButton( AlertDialog.BUTTON_POSITIVE, getString(R.string.strYes), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        retryAlert.setButton( AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNo), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    verifierEtCreerNouveauScore(joueur,score);

                    Toast.makeText(getBaseContext(), getText(R.string.sauvegarde_score), Toast.LENGTH_SHORT);
                }catch (Exception e){
                    Toast.makeText(getBaseContext(), getText(R.string.sauvegarde_pb), Toast.LENGTH_SHORT);
                }

                startActivity(new Intent(getBaseContext(), ScoresActivity.class));
                finish();
            }
        });

        retryAlert.show();
    }

    private View.OnClickListener btnCompareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i( "DEBUG", "Button clicked" );

            String strNumber = txtNumber.getText().toString();
            if ( strNumber.equals( "" ) ) return;

            int enteredValue = Integer.parseInt( strNumber );
            lblHistory.append( strNumber + "\r\n" );
            pgbScore.incrementProgressBy( 1 );
            score++;

            if ( enteredValue == searchedValue ) {
                congratulations();
            } else if ( enteredValue < searchedValue ) {
                lblResult.setText( R.string.strGreater );
            } else {
                lblResult.setText( R.string.strLower );
            }

            txtNumber.setText( "" );
            txtNumber.requestFocus();

        }
    };

}