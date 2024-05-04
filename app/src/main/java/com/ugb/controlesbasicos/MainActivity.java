package com.ugb.controlesbasicos;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempVal = findViewById(R.id.lblSensorLuz);
        activarSensorLuz();
    }

    @Override
    protected void onResume() {
        iniciar();
        super.onResume();
    }

    @Override
    protected void onPause() {
        detener();
        super.onPause();
    }

    private void activarSensorLuz(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sensor == null){
            tempVal.setText("Tu teléfono NO tiene sensor de Luz");
            finish();
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                double valor = sensorEvent.values[0];
                tempVal.setText("Luz: " + valor);
                if(valor <= 20){
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    mostrarMensaje("¡Está muy oscuro aquí!");
                } else if (valor <= 50) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    mostrarMensaje("La luz es baja.");
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    mostrarMensaje("La luz es suficiente.");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // No es necesario implementar este método en este caso
            }
        };
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void iniciar(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void detener(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}