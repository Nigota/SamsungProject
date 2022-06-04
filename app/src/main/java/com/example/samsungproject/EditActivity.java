package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle arguments = getIntent().getExtras();
        int id = Integer.parseInt(arguments.get("id").toString());
        int pos = Integer.parseInt(arguments.get("pos").toString());
        DBWords DB = new DBWords(this);
        Word word = DB.select(id);

        EditText edit_name = this.findViewById(R.id.edit_name);
        edit_name.setText(word.name);

        EditText edit_value = this.findViewById(R.id.edit_value);
        edit_value.setText(word.value);

        Button btn_del = this.findViewById(R.id.delete);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.delete(id);
                Intent result = new Intent();
                result.putExtra("pos", pos);
                setResult(RESULT_OK, result);
                Toast.makeText(getApplicationContext(), "Слово удалено", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button btn_save = this.findViewById(R.id.save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.name = edit_name.getText().toString();
                word.value = edit_value.getText().toString();
                DB.update(word);
                setResult(RESULT_CANCELED);
                Toast.makeText(getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}