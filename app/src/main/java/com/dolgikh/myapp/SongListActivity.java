package com.dolgikh.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

public class SongListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        ListView listView = findViewById(R.id.list);
        List<Song> names = MyApplication.getDatabaseHelper().getSongsList();
        ArrayAdapter<Song> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Objects.requireNonNull(names));
        listView.setAdapter(adapter);
    }
}
