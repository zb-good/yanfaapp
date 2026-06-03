package com.example.simpleapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private int tapCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView counterText = findViewById(R.id.counterText);
        Button tapButton = findViewById(R.id.tapButton);

        tapButton.setOnClickListener(view -> {
            tapCount++;
            counterText.setText(getString(R.string.counter_value, tapCount));
        });
    }
}
