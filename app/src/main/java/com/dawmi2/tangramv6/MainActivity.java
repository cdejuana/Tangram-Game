package com.dawmi2.tangramv6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bt_comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_comenzar = findViewById(R.id.bt_comenzar);

        bt_comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iComenzar = new Intent(MainActivity.this, Tangram.class);
                startActivity(iComenzar);
            }
        });
    }
}
