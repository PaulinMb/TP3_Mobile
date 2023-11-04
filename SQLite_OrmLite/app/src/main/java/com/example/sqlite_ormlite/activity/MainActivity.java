package com.example.sqlite_ormlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sqlite_ormlite.DatabaseManager;
import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.model.Score;
import com.example.sqlite_ormlite.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DatabaseManager databaseManager;
    ArrayList<Drawable> drawsPays;
    ArrayList<String> listNomsPays;

    SharedPreferences prefJoueur;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseManager = new DatabaseManager( this );
        initialiserJoueurPref();

        //Set Pays et nomPays
        setArraylistsPays();

    }

    private void initialiserJoueurPref() {
        prefJoueur = getSharedPreferences("JoueurActif", MODE_PRIVATE);
        prefEditor = prefJoueur.edit();
    }

    /*********************** SET ******************************/
    /***
     * Régler les informations de l'utilisateur connecté
     * @param user
     */
    private void setPreferenceLogin(User user){
        if (user == null) {
            prefEditor.putBoolean("Connect",false);
        }else{
            prefEditor.putBoolean("Connect",true);
            prefEditor.putInt("Id",user.getIdUser());
            prefEditor.putString("Prénom",user.getFirstName());
            prefEditor.putString("Nom",user.getLastName());
            prefEditor.putString("Courriel",user.getEmail());
            prefEditor.putString("MotDePasse",user.getMotdepasse());
            prefEditor.putString("Pays",user.getPays());
        }
        prefEditor.apply();
        prefEditor.commit();
    }

    /***
     * Insérer les données dans les tableax de drawable Pays et de String Pays
     */
    private void setArraylistsPays(){
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
    /*********************** VALIDATIONS ******************************/
    static boolean courrielEstValide(String courriel) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(courriel);
        return matcher.matches();
    }

    static boolean nomEstValide(String nom) {
        String expression = "^[\\w]{3,255}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return (pattern.matcher(nom).matches() );
    }

    static boolean motDePasseEstValide(String motdepasse) {
        String expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W]).{6,50}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(motdepasse);
        return matcher.matches();
    }




    /*********************** ORM_Lite  ******************************/
    //User
    private void creerNouveauScore(User user, int score){
        databaseManager.insertScore( new Score( user, score, new Date() ));
    }

    void seConnecter(String courriel, String motdepasse){
        User user = databaseManager.getUserForLogin(courriel,motdepasse);
        setPreferenceLogin(user);
    }

    boolean verifierUserExistant(User newUser){
        User user = databaseManager.getUserForLogin(newUser.getEmail(),newUser.getMotdepasse());
        return user==null ? false : true ;
    }

    //Score
    int creerNouvelUtilisateur(User newUser){
        return databaseManager.insertUser(newUser);
    }

    private void mettreAJourScore(int idUser, int score){
        databaseManager.updateScore(idUser, score);
    }

    void verifierEtCreerNouveauScore(User user, int newScore){
        Score score = databaseManager.selectScoreWhereUserId(user.getIdUser());
        if (score == null) {
            creerNouveauScore(user, newScore);
        } else {
            if (score.getScore() > newScore) mettreAJourScore(user.getIdUser(), newScore);
        }

    }




}