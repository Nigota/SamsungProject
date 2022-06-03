package com.example.samsungproject;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class LearnFragment extends Fragment {
    int word_id = 0;
    ArrayList<Word> words;
    CardView card;
    TextView text_front, text_back, message;
    Button btn_yes, btn_no;
    DBWords DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        DB = new DBWords(getContext());
        words = DB.selectByCurTime();
        text_front = view.findViewById(R.id.text_front);
        text_back = view.findViewById(R.id.text_back);
        message = view.findViewById(R.id.mes);
        card = view.findViewById(R.id.card);
        btn_no = view.findViewById(R.id.btn_no);
        btn_yes = view.findViewById(R.id.btn_yes);
        LinearLayout layout = view.findViewById(R.id.ln);

        if (words.size() == 0) {
            hideLearn();
        } else {
            newWord();
        }

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStage(-1);
                newWord();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStage(+1);
                newWord();
            }
        });

        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = (text_back.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                text_back.setVisibility(v);
            }
        });
        return view;
    }

    public void hideLearn() {
        card.setVisibility(View.GONE);
        btn_yes.setVisibility(View.GONE);
        btn_no.setVisibility(View.GONE);
        message.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "На сегодня все выучено!", Toast.LENGTH_SHORT).show();
    }

    public void setStage(int v) {
        int stage = words.get(word_id).stage + v;
        if (stage >= 0 && stage <= 19) {
            words.get(word_id).stage = stage;
        }
        setNextTime();
        updateWord();
        word_id += 1;
    }

    public void setNextTime() {
        int stage = words.get(word_id).stage;
        if (stage > 0 && stage <= 4) {
            words.get(word_id).next_time += 4 * 60 * 1000;
        } else if (stage > 4 && stage <= 9) {
            words.get(word_id).next_time += 40 * 60 * 1000;
        } else if (stage > 9 && stage <= 14) {
            words.get(word_id).next_time += 2 * 60 * 60 * 1000;
        } else {
            words.get(word_id).next_time += 12 * 60 * 60 * 1000;
        }
    }

    public void updateWord() {
        DB.update(words.get(word_id));
    }

    public void newWord() {
        if (word_id >= words.size()) {
            hideLearn();
        } else {
            text_back.setVisibility(View.GONE);
            text_back.setText(words.get(word_id).value);
            text_front.setText(words.get(word_id).name);
        }
    }
}