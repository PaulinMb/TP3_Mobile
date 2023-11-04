package com.example.sqlite_ormlite.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.databinding.ActivityInscriptionBinding;
import com.example.sqlite_ormlite.model.User;
import com.example.sqlite_ormlite.util.adapter.SpinerPaysAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptionActivity extends MainActivity {

    private ActivityInscriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Titre Spannable
        SpannableString spanNomPays = new SpannableString("Inscription");
        spanNomPays.setSpan(new RelativeSizeSpan(3f) ,0, spanNomPays.length(),0);
        spanNomPays.setSpan(new StyleSpan(Typeface.ITALIC),0, spanNomPays.length(),0);
        binding.textViewSpan.setText(spanNomPays);


        //--- Zones de saisie ---
        afficherErreurSaisies();

        //--- Spinner ---
        setSpinnerPays();

        //--- les buttons ---
        binding.buttonInscrire.setOnClickListener(view -> {
            creerUser();
        });
        //--- ---
    }

    /***
     * Fonction pour créer un nouvel utilisateur
     */
    private void creerUser(){
        User newUser;

        //-- Vérification
        if(nomEstValide(getPrenom())
                && nomEstValide(getNom())
                && motDePasseEstValide(getMotDePasse())
                && courrielEstValide(getCourriel()) ){
            //-- Création de l'utilisateur
            newUser = new User(getPrenom(), getNom(), getCourriel());
            newUser.setMotdepasse( getMotDePasse());
            newUser.setPays(listNomsPays.get(getPays()));

            //-- Vérification de compte || Création

            if (!verifierUserExistant(newUser)){
                int etat = creerNouvelUtilisateur(newUser);
                alertInscrit(etat);
            } else{
                alertInscrit(0);
            }
        }else {
            alertInscrit(-1);
        }
    }
    /***
     * Faire afficher une boite de dialog Alert en fonction de l'état de l'inscription
     */
    private void alertInscrit (int etat){
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        if(etat == 1) {
            SpannableString spanString = new SpannableString(getString(R.string.inscrit_titre_reussie));
            spanString.setSpan(new ForegroundColorSpan(Color.GREEN),0,spanString.length(),0);

            dialog.setTitle(spanString);
            dialog.setMessage(getString(R.string.inscrit_text_reussie));
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.inscrit_bouton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        }else if (etat == 0){
            SpannableString spanString = new SpannableString(getString(R.string.inscrit_titre_existe));
            spanString.setSpan(new ForegroundColorSpan(Color.CYAN),0,spanString.length(),0);

            dialog.setTitle(spanString);
            dialog.setMessage(getString(R.string.inscrit_text_existe));
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.inscrit_bouton),(dialogInterface,i) -> {});
        }else if (etat == -2){
            SpannableString spanString = new SpannableString(getString(R.string.inscrit_titre_courriel));
            spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY),0,spanString.length(),0);

            dialog.setTitle(spanString);
            dialog.setMessage(getString(R.string.inscrit_text_courriel));
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.inscrit_bouton),(dialogInterface,i) -> {});
        }else {
            SpannableString spanString = new SpannableString( getString(R.string.inscrit_titre_echec));
            spanString.setSpan(new ForegroundColorSpan(Color.RED),0,spanString.length(),0);

            dialog.setTitle(spanString);
            dialog.setMessage(getString(R.string.inscrit_text_echec));
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.inscrit_bouton),(dialogInterface,i) -> {});
        }

        dialog.show();
    }


    /***
     * Faire afficher les différents messages d'erreur en fonction d'une zone de saisie
     */
    private void afficherErreurSaisies(){
        binding.editTextPrenom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!nomEstValide(getPrenom())) {
                    binding.editTextPrenom.setError(getString(R.string.inscrit_change_prenom));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.editTextNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!nomEstValide(getNom())) {
                    binding.editTextNom.setError(getString(R.string.inscrit_change_nom));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.editTextCourriel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!courrielEstValide(getCourriel())) {
                    binding.editTextCourriel.setError(getString(R.string.inscrit_change_courriel));
                }
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
                    binding.editTextPassword.setError(getString(R.string.inscrit_change_motdepasse));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    };


    /*********************** GET-SET ******************************/
    private String getPrenom(){
        return binding.editTextPrenom.getText().toString();
    }
    private String getNom(){
        return binding.editTextNom.getText().toString();
    }
    private String getCourriel(){
        return binding.editTextCourriel.getText().toString();
    }
    private String getMotDePasse(){
        return binding.editTextPassword.getText().toString();
    }
    private int getPays() {return binding.spinnerPays.getSelectedItemPosition();}

    /***
     * Set le spinner de pays
     */
    private void setSpinnerPays(){
        SpinerPaysAdapter imagesAd = new SpinerPaysAdapter(this, drawsPays, listNomsPays);
        binding.spinnerPays.setAdapter(imagesAd);
    }




}