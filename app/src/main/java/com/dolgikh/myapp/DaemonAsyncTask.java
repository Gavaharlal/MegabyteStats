package com.dolgikh.myapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DaemonAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String urlString = "http://media.ifmo.ru/api_get_current_song.php";
    private final String requestParameters = "login=4707login&password=4707pass";
    private final long SLEEPING = 20 * 1000;

    @Override
    protected Void doInBackground(Void... voids) {
        DatabaseHelper databaseHelper = MyApplication.getDatabaseHelper();
        try {
            URL url = new URL(urlString);
            while (true) {
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestParameters.getBytes());
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONObject json = new JSONObject(in.readLine());
                String songSignature = json.getString("info");
                Song currentSong = new Song(songSignature, System.currentTimeMillis());
                Song lastSong = databaseHelper.getLastSong();
                if (!currentSong.equals(lastSong)) {
                    databaseHelper.addSong(currentSong);
                }
                Thread.sleep(SLEEPING);
            }
        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
