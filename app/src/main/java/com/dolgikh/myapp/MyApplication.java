package com.dolgikh.myapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyApplication extends Application {

    private static DatabaseHelper DATABASE_HELPER;

    @Override
    public void onCreate() {
        super.onCreate();
        DATABASE_HELPER = new DatabaseHelper(this);
        if (checkConnection()){
            DaemonAsyncTask daemonAsyncTask = new DaemonAsyncTask();
            daemonAsyncTask.execute();
        }
    }

    private boolean checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast toast = Toast.makeText(this, "Отсутствует подключение к интернету.\nПриложение будет работать  в автономном режиме", Toast.LENGTH_LONG);
            toast.show();
            return false;
        } else {
            return true;
        }
    }

    public static DatabaseHelper getDatabaseHelper() {
        return DATABASE_HELPER;
    }
}
