package com.example.sqlite_ormlite.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.databinding.ActivityConnexionActvityBinding; // comment faire pour quil importer databinding qui nest pas reconnu


public class ConnexionActvity extends MainActivity {

    private ActivityConnexionActvityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //--- Zones de saisie ---
        afficherErreurSaisies();

        //--- les buttons ---
        binding.buttonConnecter.setEnabled(false);
        binding.buttonConnecter.setOnClickListener(view ->{
            seConnecter(getCourriel(),getMotDePasse());

            //--- activité suivante (jeu)
            if(prefJoueur.getBoolean("Connect",true)){
                Intent jeu = new Intent(this, JeuActivity.class);
                jeu.putExtra("id",   prefJoueur.getInt("Id",0));

                String[] str = {prefJoueur.getString("Prénom","p")
                        ,  prefJoueur.getString("Nom","n")
                        ,  prefJoueur.getString("Courriel","c")
                        ,  prefJoueur.getString("MotDePasse","mdp")
                        ,  prefJoueur.getString("Pays","p")};
                jeu.putExtra("connect", str);
                startActivity(jeu);
            } else {
                alertInscrit();
            }
        });

        binding.buttonInscrire.setOnClickListener(view -> {
            this.startActivity(new Intent(this, InscriptionActivity.class));
        } );

    }

    /***
     * Une boite de dialog Alert apparait lorsque l'authentification échoue
     */
    private void alertInscrit (){
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        dialog.setTitle(getString(R.string.connect_titre_echec));
        dialog.setMessage(getString(R.string.connect_text_echec));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.inscrit_bouton), ( dialogInterface,  i) -> {});

        dialog.show();
    }

    /***
     * Faire afficher les différents messages d'erreur en fonction d'une zone de saisie
     */
    private void afficherErreurSaisies(){
        binding.editTextCourriel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!courrielEstValide(getCourriel())) {
                    binding.editTextCourriel.setError(getString(R.string.connect_change_courriel));
                }
                connexionEstPermise();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!motDePasseEstValide(getMotDePasse())) {
                    binding.editTextPassword.setError(getString(R.string.connect_change_motdepasse));
                }
                connexionEstPermise();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void connexionEstPermise(){
        binding.buttonConnecter.setEnabled(motDePasseEstValide(getMotDePasse()) && courrielEstValide(getCourriel()));
    }


    /*********************** GET-SET ******************************/
    private String getCourriel(){
        return binding.editTextCourriel.getText().toString();
    }
    private String getMotDePasse(){
        return binding.editTextPassword.getText().toString();
    }

}