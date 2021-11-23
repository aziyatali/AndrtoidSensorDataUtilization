package com.example.sensor_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor, gyroscope;
    SensorEventListener sensorEventListener, gyroscopeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_accelerometer);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // sensor for accelerometer
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (sensor == null){
            Toast.makeText(this, "value is null", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (gyroscope == null){
            Toast.makeText(this, "value is null", Toast.LENGTH_SHORT).show();
            finish();
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0], y = event.values[1], z = event.values[2];
                textView.setText(x + "\n" + y + "\n" + z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0], y = event.values[1], z = event.values[2];
                textView.setText("Gyroscope: "+ x + "\n" + y + "\n" + z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph2.addSeries(series2);


    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(gyroscopeListener);
        sensorManager.unregisterListener(sensorEventListener);
    }

}