package com.example.servicio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.content.Intent;

public class PantallaPeople extends AppCompatActivity {
    public static final String URL_SWAPI = "https://swapi.co/api/";
    private Button Anterior;
    private Button Siguiente;
    private Button Regresar;
    private TextView nombre;
    private TextView estatura;
    private TextView peso;
    private TextView colorCabello;
    private TextView colorPiel;
    private TextView colorOjos;
    private TextView cumpleaños;
    private TextView género;
    String nameResponse = "";
    String heightResponse = "";
    String massResponse = "";
    String hair_colorResponse = "";
    String skin_colorResponse = "";
    String eye_colorResponse = "";
    String birth_yearResponse = "";
    String genderResponse = "";
    Integer número = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_people);

        Siguiente    = (Button)   findViewById(R.id.button_Siguiente);
        Regresar     = (Button)   findViewById(R.id.button_Regresar);
        nombre       = (TextView) findViewById(R.id.text_name);
        estatura     = (TextView) findViewById(R.id.text_height);
        peso         = (TextView) findViewById(R.id.text_mass);
        colorCabello = (TextView) findViewById(R.id.text_hair_color);
        colorPiel    = (TextView) findViewById(R.id.text_skin_color);
        colorOjos    = (TextView) findViewById(R.id.text_eye_color);
        cumpleaños   = (TextView) findViewById(R.id.text_birth_year);
        género       = (TextView) findViewById(R.id.text_gender);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPrincipal();
            }
        });
    }
    public void pasarPrincipal() {
        Intent intent = new Intent (PantallaPeople.this , PantallaPrincipal.class );
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
                    URL urlService = new URL (URL_SWAPI + "people/" + número + "/" );
                    HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200 ) {
                        // Success
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object

                        String value = "";
                        Log.v("INFO",value);
                        nameResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        heightResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        massResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        hair_colorResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        skin_colorResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        eye_colorResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        birth_yearResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        genderResponse = jsonReader.nextName() + ": " + jsonReader.nextString();
                        nombre.post(new Runnable() {
                            @Override
                            public void run() {
                                nombre.setText(nameResponse);
                                estatura.setText(heightResponse);
                                peso.setText(massResponse);
                                colorCabello.setText(hair_colorResponse);
                                colorPiel.setText(skin_colorResponse);
                                colorOjos.setText(eye_colorResponse);
                                cumpleaños.setText(birth_yearResponse);
                                género.setText(genderResponse);
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
        estatura.setText(heightResponse);
        peso.setText(massResponse);
        colorCabello.setText(hair_colorResponse);
        colorPiel.setText(skin_colorResponse);
        colorOjos.setText(eye_colorResponse);
        cumpleaños.setText(birth_yearResponse);
        género.setText(genderResponse);
    } // end callWebService()
}