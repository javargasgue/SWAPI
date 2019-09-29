package com.example.servicio;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class PantallaPrincipal extends AppCompatActivity {
    private Button People;
    private Button Planets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        People =(Button) findViewById(R.id.button_People);
        Planets =(Button) findViewById(R.id.button_Planets);
        People.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarAPeople();
            }
        });
        Planets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarAPlanets();
            }
        });
    }
    public void pasarAPeople() {
        Intent intent = new Intent (PantallaPrincipal.this , PantallaPeople.class );
        startActivity(intent);
    }
    public void pasarAPlanets() {
        Intent intent = new Intent (PantallaPrincipal.this , PantallaPlanets.class );
        startActivity(intent);
    }
}