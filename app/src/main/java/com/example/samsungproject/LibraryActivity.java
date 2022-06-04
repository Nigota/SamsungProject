package com.example.samsungproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class LibraryActivity extends AppCompatActivity {
    static ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        DBWords DB = new DBWords(this);

        WordAdapter adapter = new WordAdapter(this, DB.selectAll());
        ListView listView = this.findViewById(R.id.lib_list);
        listView.setAdapter(adapter);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bundle data = result.getData().getExtras();
                            int pos = Integer.parseInt(data.get("pos").toString());
                            adapter.remove(adapter.getItem(pos));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}