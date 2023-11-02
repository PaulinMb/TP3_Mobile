package com.example.sqlite_ormlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoresView;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoresView = (TextView) findViewById( R.id.scoresView );
        databaseManager = new DatabaseManager( this );

      User james = new User( "james", "bond", "bond@mi6.ca" );
        User jason = new User( "jason", "bourne", "bourne@cia.com" );
        databaseManager.insertScore( new Score( james, 5, new Date() ));
       databaseManager.insertScore( new Score( jason, 8, new Date() ));

        List<Score> scores = databaseManager.readScores();
        for( Score score : scores ) {
            scoresView.append( score.toString() + "\n\n" );
        }
        databaseManager.close();
    }
}