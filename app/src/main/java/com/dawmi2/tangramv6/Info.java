package com.dawmi2.tangramv6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    private TextView tv_puntos;
    private TextView tv_nivel;
    private TextView tv_completadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tv_puntos = findViewById(R.id.tv_n_puntos);
        tv_nivel = findViewById(R.id.tv_num_nivel);
        tv_completadas = findViewById(R.id.tv_num_fig_comp);

        int puntuacion = getIntent().getIntExtra("PUNTUACION", 0);
        int nivel = getIntent().getIntExtra("NIVEL", 0);
        int completadas = getIntent().getIntExtra("COMPLETADAS", 0);

        tv_puntos.setText(Integer.toString(puntuacion));
        tv_nivel.setText(nivel+"");
        tv_completadas.setText(completadas+"");
    }
}
