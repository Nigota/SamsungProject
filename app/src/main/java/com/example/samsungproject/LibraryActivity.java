package com.example.samsungproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class LibraryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        DBWords DB = new DBWords(this);

        WordAdapter adapter = new WordAdapter(this, DB.selectAll());
        ListView listView = this.findViewById(R.id.lib_list);
        listView.setAdapter(adapter);
    }
}