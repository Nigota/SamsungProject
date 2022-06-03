package com.example.samsungproject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btn_add = this.findViewById(R.id.btn_add);
        EditText name = this.findViewById(R.id.name);
        EditText value = this.findViewById(R.id.value);
        Context context = this;
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBWords DB = new DBWords(context);
                DB.insert(name.getText().toString(), value.getText().toString());
                finish();
            }
        });
    }
}