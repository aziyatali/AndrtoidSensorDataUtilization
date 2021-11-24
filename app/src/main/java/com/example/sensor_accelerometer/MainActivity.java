package com.example.sensor_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity{

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor, gyroscope;
    SensorEventListener sensorEventListener, gyroscopeListener;
    private double acceleratorCurrent;
    private double acceleratorPrevious;
    private double gyroscopeCurrent;
    private double gyroscopePrevious;
    private int pointsPlotted = 5;
    private int pointsPlottedG = 5;
    private Viewport viewport1;
    private Viewport viewport2;

    // Initialization of the graph and default values for accelerators
    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });

    // Initialization of the graph and default values for gyroscope
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });
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
                acceleratorCurrent = Math.sqrt((x * x + y * y + z * z));
                double  changeInAcceleration = Math.abs(acceleratorCurrent - acceleratorPrevious);
                acceleratorPrevious = acceleratorCurrent;

                pointsPlotted++;
                series.appendData(new DataPoint(pointsPlotted, changeInAcceleration), true, pointsPlotted);
                viewport1.setMaxX(pointsPlotted);
                viewport1.setMinX(pointsPlotted-200);
//                textView.setText(x + "\n" + y + "\n" + z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0], y = event.values[1], z = event.values[2];
                gyroscopeCurrent = Math.sqrt((x * x + y * y + z * z));
                double changeInGyroscope = Math.abs(gyroscopeCurrent - gyroscopePrevious);
                gyroscopePrevious = gyroscopeCurrent;
                pointsPlottedG++;
                series2.appendData(new DataPoint(pointsPlottedG, changeInGyroscope), true, pointsPlottedG);
                viewport2.setMaxX(pointsPlottedG);
                viewport2.setMinX(pointsPlottedG - 200);
//                textView.setText("Gyroscope: "+ x + "\n" + y + "\n" + z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport1 = graph.getViewport();
        viewport1.setScrollable(true);
        viewport1.setXAxisBoundsManual(true);


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        viewport2 = graph2.getViewport();
        viewport2.setScrollable(true);
        viewport2.setXAxisBoundsManual(true);
        series2.setColor(Color.RED);

        Button btn = findViewById(R.id.startButton);
        Button btn2 = findViewById(R.id.pauseButton);
        Button btn3 = findViewById(R.id.restartButton);

        // Start the graph
        btn.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                graph.addSeries(series);


                graph.addSeries(series2);
                graph2.addSeries(series2);

            }
        });

        // Pause the graph
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Restart the graph
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.removeAllSeries();

                btn.setOnClickListener (new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        graph.addSeries(series);


                        graph.addSeries(series2);
                        graph2.addSeries(series2);

                    }
                });
            }
        });



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