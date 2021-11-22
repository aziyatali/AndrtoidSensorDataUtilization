package com.example.sensor_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;
//    int isClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_accelerometer);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, sensor, sensorManager.SENSOR_DELAY_NORMAL);

    }

    // Change the values of x, y, z onSensor
    @Override
    public void onSensorChanged(SensorEvent event){
        float x = event.values[0], y = event.values[1], z = event.values[2];
        textView.setText(x + "\n" + y + "\n" + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

//    public void onClick(View view){
//        isClicked++;
//        isClicked%=2;
//        TextView text = findViewById(R.id.text_accelerometer);
//    }




}