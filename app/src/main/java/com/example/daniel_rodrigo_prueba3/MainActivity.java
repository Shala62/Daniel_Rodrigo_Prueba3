package com.example.daniel_rodrigo_prueba3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private EditText nombreUs;
    private ProgressBar progressBar;
    private int counter = 0;

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

        nombreUs = findViewById(R.id.editNombre);
        progressBar = findViewById(R.id.progressBar);
    }
    public void Siguiente(View view) {
        String nombreS = nombreUs.getText().toString();
        if (nombreS.length() == 0) {
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show();
        }
        if (nombreS.length() != 0) {
            progressBar.setVisibility(View.VISIBLE);
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    counter++;
                    progressBar.setProgress(counter);
                    if (counter == 30) {
                        timer.cancel();
                        Intent intent = new Intent(MainActivity.this, listado.class);
                        startActivity(intent);
                    }
                }
            };
            timer.schedule(timerTask, 30, 30);
        }
    }

}