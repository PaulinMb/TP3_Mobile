package com.example.partiemysql;



import android.app.Application;

public class GlobalClass extends Application {
    public static final String BASE_URL = "http://10.0.0.1:3306/app/";
//    public static final String BASE_URL = "http:/localhost:3306/androidApp/api.php";

    private static GlobalClass singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static GlobalClass getInstance() {
        return singleton;
    }
}
