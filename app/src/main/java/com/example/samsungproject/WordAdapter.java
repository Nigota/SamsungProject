package com.example.samsungproject;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Context context, ArrayList<Word> arr) {
        super(context, R.layout.adapter_item, arr);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

        TextView text_front = convertView.findViewById(R.id.text_front);
        TextView text_back = convertView.findViewById(R.id.text_back);
        LinearLayout layout = convertView.findViewById(R.id.ln);

        text_front.setText(word.name);
        text_back.setText(word.value);

        CardView cardView = convertView.findViewById(R.id.card);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v = (text_back.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                text_back.setVisibility(v);
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("id", word.id);
                intent.putExtra("pos", position);
                LibraryActivity.someActivityResultLauncher.launch(intent);
                return false;
            }
        });

        return convertView;
    }
}
