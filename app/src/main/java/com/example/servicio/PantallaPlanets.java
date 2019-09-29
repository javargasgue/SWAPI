package com.example.servicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PantallaPlanets extends AppCompatActivity {
    public static final String URL_SWAPI = "https://swapi.co/api/";

    private Button Anterior;
    private Button Siguiente;
    private Button Regresar;
    private TextView nombre;
    private TextView periodoRotación;
    private TextView periodoOrbital;
    private TextView diámetro;
    private TextView clima;
    private TextView gravedad;
    private TextView terreno;
    private TextView aguaSuperficial;
    private TextView población;
    String nameResponse = "";
    String rotation_periodResponse = "";
    String orbital_periodResponse = "";
    String diameterResponse = "";
    String climateResponse = "";
    String gravityResponse = "";
    String terrainResponse = "";
    String surface_waterResponse = "";
    String surface_population = "";
    Integer número = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_planets);

        Anterior        = (Button)   findViewById(R.id.button_Anterior);
        Siguiente       = (Button)   findViewById(R.id.button_Siguiente);
        Regresar        = (Button)   findViewById(R.id.button_Regresar);
        nombre          = (TextView) findViewById(R.id.text_name);
        periodoRotación = (TextView) findViewById(R.id.text_rotation_period);
        periodoOrbital  = (TextView) findViewById(R.id.text_orbital_period);
        diámetro        = (TextView) findViewById(R.id.text_diameter);
        clima           = (TextView) findViewById(R.id.text_climate);
        gravedad        = (TextView) findViewById(R.id.text_gravity);
        terreno         = (TextView) findViewById(R.id.text_terrain);
        aguaSuperficial = (TextView) findViewById(R.id.text_surface_water);
        población       = (TextView) findViewById(R.id.text_population);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPrincipal();
            }
        });

    }
    public void pasarPrincipal() {
        Intent intent = new Intent (PantallaPlanets.this , PantallaPrincipal.class );
        startActivity(intent);
    }
    public void onClickAnterior (View v) {
        if(número == 1){
            número = 10 ;
        }else {
            número = número - 1;
        }
        callWebService("");
    }
    public void onClickSiguiente (View v) {
        if(número == 10){
            número = 1 ;
        }else {
            número = número + 1;
        }
        callWebService("");
    }
    public void callWebService(String serviceEndPoint){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = new URL (URL_SWAPI + "planets/" + número + "/" );
                    HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        // Success
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object

                        String value = "";
                        Log.v("INFO",value);
                        nameResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        rotation_periodResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        orbital_periodResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        diameterResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        climateResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        gravityResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        terrainResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        surface_waterResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        surface_population = jsonReader.nextName() + ": " + jsonReader.nextString();
                        nombre.post(new Runnable() {
                            @Override
                            public void run() {
                                nombre.setText(nameResponse);
                                periodoRotación.setText(rotation_periodResponse);
                                periodoOrbital.setText(orbital_periodResponse);
                                diámetro.setText(diameterResponse);
                                clima.setText(climateResponse);
                                gravedad.setText(gravityResponse);
                                terreno.setText(terrainResponse);
                                aguaSuperficial.setText(surface_waterResponse);
                                población.setText(surface_population);
                            }
                        });

                    } else {
                        // Error handling code goes here
                        Log.v("ERROR", "ERROR");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        nombre.setText(nameResponse);
        periodoRotación.setText(rotation_periodResponse);
        periodoOrbital.setText(orbital_periodResponse);
        diámetro.setText(diameterResponse);
        clima.setText(climateResponse);
        gravedad.setText(gravityResponse);
        terreno.setText(terrainResponse);
        aguaSuperficial.setText(surface_waterResponse);
        población.setText(surface_population);
    } // end callWebService()
}
