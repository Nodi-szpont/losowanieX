package com.example.losowanie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random;
    private TextView textView;
    private Handler handler;
    private List<Integer> dostepneLiczby;
    private List<Integer> liczbyPrzyciskow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        random = new Random();
        textView = findViewById(R.id.textView);
        handler = new Handler(Looper.getMainLooper());
        dostepneLiczby = new ArrayList<>();
        liczbyPrzyciskow = new ArrayList<>();

        inicjalizujLiczby();
        uruchomLosowanieLiczby();

        skonfigurujPrzycisk(findViewById(R.id.button));
        skonfigurujPrzycisk(findViewById(R.id.button3));
        skonfigurujPrzycisk(findViewById(R.id.button4));
        skonfigurujPrzycisk(findViewById(R.id.button5));
        skonfigurujPrzycisk(findViewById(R.id.button6));
    }

    private void inicjalizujLiczby() {
        dostepneLiczby.clear();
        liczbyPrzyciskow.clear();
        for (int i = 1; i <= 10; i++) {
            dostepneLiczby.add(i);
        }
        Collections.shuffle(dostepneLiczby);
    }

    private void uruchomLosowanieLiczby() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dostepneLiczby.isEmpty()) {
                    inicjalizujLiczby();
                }
                textView.setText(String.valueOf(dostepneLiczby.remove(0)));
                handler.postDelayed(this, 5000);
            }
        });
    }

    private void skonfigurujPrzycisk(Button button) {
        int randomNumber = losujLiczbeDoGuzika();
        button.setText(String.valueOf(randomNumber));

        button.setOnClickListener(v -> {
            button.setBackgroundColor(Color.GREEN);
        });
    }

    private int losujLiczbeDoGuzika() {
        if (dostepneLiczby.isEmpty()) {
            inicjalizujLiczby();
        }
        int liczba = dostepneLiczby.remove(0);
        liczbyPrzyciskow.add(liczba);
        return liczba;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}