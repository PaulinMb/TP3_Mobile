package com.example.sqlite_ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sqlite_ormlite.model.Score;
import com.example.sqlite_ormlite.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;


import com.j256.ormlite.table.TableUtils;

import java.util.Date;
import java.util.List;


public class DatabaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Game.db";
    private static final int DATABASE_VERSION = 4;

    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable( connectionSource, Score.class );
            TableUtils.createTable( connectionSource, User.class );
            Log.i( "DATABASE", "onCreate invoked" );
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't create Database", exception );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable( connectionSource, Score.class, true );
            TableUtils.dropTable( connectionSource, User.class, true );
            onCreate( database, connectionSource);
            Log.i( "DATABASE", "onUpgrade invoked" );
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't upgrade Database", exception );
        }
    }


    //Users

    /***
     * Insert dans la BD un nouvel utilisateur
     * @param user
     * @return
     */
    public int insertUser(User user){
        try {
            Dao<User, Integer> dao = getDao( User.class );
            dao.createIfNotExists( user );
            Log.i( "DATABASE", "insertUser invoked" );
            return 1;
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't insert new user into Database", exception );
            return -2;
        }

    }

    /***
     * Choisie un utilisateur existant pour la connexion
     * @param email
     * @param password
     * @return
     */
    public User getUserForLogin(String email, String password){

        try {
            Dao<User, Integer> dao = getDao( User.class );
            User result = dao.queryBuilder().where()
                    .eq("email",email).and()
                    .eq("motdepasse",password)
                    .query().get(0);

            Log.i( "DATABASE", "getUserForLogin invoked" );
            return result;
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't get user from Database", exception );
            return null;
        }
    }

    //Scores
    public void insertScore( Score score ) {
        try {
            Dao<Score, Integer> dao = getDao( Score.class );
            dao.createIfNotExists( score );
            Log.i( "DATABASE", "insertScore invoked" );
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't insert score into Database", exception );
        }
    }

    /***
     * Mettra a jour le nouveau score du joueur précédent
     * @param userId
     * @param newScore
     */
    public void updateScore(int userId, int newScore) {
        try {
            Dao<Score, Integer> dao = getDao(Score.class);
            UpdateBuilder<Score, Integer> updateScore = dao.updateBuilder();

            updateScore.where().eq("user_idUser", userId);
            updateScore.updateColumnValue("score", newScore);
            updateScore.updateColumnValue("when", new Date()); // Update the date in the same call
            updateScore.update();

            Log.i("DATABASE", "updateScore invoked");
        } catch (Exception exception) {
            Log.e("DATABASE", "Can't update score into Database", exception);
        }
    }

    public Score selectScoreWhereUserId( int value ) {
        try {
            Dao<Score, Integer> dao = getDao( Score.class );
            Score result = dao.queryBuilder().where()
                    .eq("user_idUser",value).query().get(0);
            Log.i( "DATABASE", "selectScoreWhereUserId invoked" );
            return result;
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't select score from idUser into Database", exception );
            return null;
        }
    }



    public List<Score> readScores() {
        try {
            Dao<Score, Integer> dao = getDao(Score.class);
            List<Score> scores = dao.queryBuilder().orderByRaw("`score` ASC").query();
            Log.i("DATABASE", "readScores invoked. Number of scores: " + scores.size());
            return scores;
        } catch (Exception exception) {
            Log.e("DATABASE", "Can't read scores from Database", exception);
            return null;
        }
    }

    public List<Score> getAllScoresOrderedByScore() {
        try {
            Dao<Score, Integer> dao = getDao(Score.class);
            return dao.queryBuilder().orderBy("score", true).query();
        } catch (Exception exception) {
            Log.e("DATABASE", "Can't get scores from Database", exception);
            return null;
        }
    }
}