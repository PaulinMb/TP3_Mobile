package com.example.sqlite_ormlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sqlite_ormlite.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;

import com.example.sqlite_ormlite.R;
import com.example.sqlite_ormlite.databinding.ActivityScoresBinding;
import com.example.sqlite_ormlite.model.Score;
import com.example.sqlite_ormlite.util.adapter.GetScoreAdapter;

import java.util.List;

public class ScoresActivity extends MainActivity {

    private ActivityScoresBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //--- Titre ---
        SpannableString sourligne = new SpannableString(getString(R.string.text_score));
        sourligne.setSpan(new RelativeSizeSpan(2f) ,0, sourligne.length(),0);
        sourligne.setSpan(new UnderlineSpan(),0, sourligne.length(),0);
        binding.textViewScoreActivity.setText(sourligne);

        //--- Bouton retour ---
        binding.buttonRetour.setText(R.string.btnRetour_score);
        binding.buttonRetour.setOnClickListener(view -> {
            finish();
            Intent connAct = new Intent(this, ConnexionActvity.class);
            startActivity(connAct);
        });

        //Get all scores
        getTableauDesScores();
    }

    private void getTableauDesScores(){
        List<Score> scores = databaseManager.readScores();

        GetScoreAdapter adapter = new GetScoreAdapter(this, scores,drawsPays, listNomsPays);
        binding.layoutScores.setAdapter(adapter);

        databaseManager.close();
    }


}