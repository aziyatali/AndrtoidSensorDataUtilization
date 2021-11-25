package com.example.sensor_accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity{
    AnimationDrawable rocketAnimation;
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor, gyroscope;
    SensorEventListener sensorEventListener, gyroscopeListener;
    private double acceleratorCurrentX;
    private double acceleratorCurrentY;
    private double acceleratorCurrentZ;
    private double acceleratorPreviousX;
    private double acceleratorPreviousY;
    private double acceleratorPreviousZ;
    private double gyroscopeCurrentX;
    private double gyroscopeCurrentY;
    private double gyroscopeCurrentZ;
    private double gyroscopePreviousX;
    private double gyroscopePreviousY;
    private double gyroscopePreviousZ;
    private int pointsPlotted = 5;
    private int pointsPlottedG = 5;
    private Viewport viewport1;
    private Viewport viewport2;

    // Initialization of the graph and default values for accelerators
    LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });
    LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });
    LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });

    // Initialization of the graph and default values for gyroscope
    LineGraphSeries<DataPoint> series2X = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });
    LineGraphSeries<DataPoint> series2Y = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0),
            new DataPoint(0, 0)
    });
    LineGraphSeries<DataPoint> series2Z = new LineGraphSeries<DataPoint>(new DataPoint[] {
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

        Button start = (Button)findViewById(R.id.startButton);
        ImageView run = (ImageView) findViewById(R.id.action_image);
        AnimationDrawable runningMan = (AnimationDrawable)run.getDrawable();

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
                acceleratorCurrentX = x;
                acceleratorCurrentY = y;
                acceleratorCurrentZ = z;

                // we calculate the speed of Man by this formula
                int speedOfMan = (int)Math.sqrt((x*x + y*y + z*z));
                //set the speed for man according to accelerometer.
                //runningMan.setDuration(speedOfMan);
                runningMan.start();
                double  changeInAccelerationX = Math.abs(acceleratorCurrentX - acceleratorPreviousX);
                double  changeInAccelerationY = Math.abs(acceleratorCurrentY - acceleratorPreviousY);
                double  changeInAccelerationZ = Math.abs(acceleratorCurrentZ - acceleratorPreviousZ);

                acceleratorPreviousX = acceleratorCurrentX;
                acceleratorPreviousY = acceleratorCurrentY;
                acceleratorPreviousZ = acceleratorCurrentZ;


                pointsPlotted++;
                seriesX.appendData(new DataPoint(pointsPlotted, changeInAccelerationX), true, pointsPlotted);
                seriesY.appendData(new DataPoint(pointsPlotted, changeInAccelerationY), true, pointsPlotted);
                seriesZ.appendData(new DataPoint(pointsPlotted, changeInAccelerationZ), true, pointsPlotted);
                viewport1.setMaxX(pointsPlotted);
                viewport1.setMinX(pointsPlotted-200);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0], y = event.values[1], z = event.values[2];
                //gyroscopeCurrent = Math.sqrt((x * x + y * y + z * z));
                gyroscopeCurrentX = x;
                gyroscopeCurrentY = y;
                gyroscopeCurrentZ = z;
                double changeInGyroscopeX = Math.abs(gyroscopeCurrentX - gyroscopePreviousX);
                double changeInGyroscopeY = Math.abs(gyroscopeCurrentY - gyroscopePreviousY);
                double changeInGyroscopeZ = Math.abs(gyroscopeCurrentZ - gyroscopePreviousZ);
                gyroscopePreviousX = gyroscopeCurrentX;
                gyroscopePreviousY = gyroscopeCurrentY;
                gyroscopePreviousZ = gyroscopeCurrentZ;
                pointsPlottedG++;
                series2X.appendData(new DataPoint(pointsPlottedG, changeInGyroscopeX), true, pointsPlottedG);
                series2Y.appendData(new DataPoint(pointsPlottedG, changeInGyroscopeY), true, pointsPlottedG);
                series2Z.appendData(new DataPoint(pointsPlottedG, changeInGyroscopeZ), true, pointsPlottedG);

                viewport2.setMaxX(pointsPlottedG);
                viewport2.setMinX(pointsPlottedG - 200);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport1 = graph.getViewport();
        viewport1.setScrollable(true);
        viewport1.setXAxisBoundsManual(true);
        seriesX.setColor(Color.RED);
        seriesY.setColor(Color.BLACK);
        seriesZ.setColor(Color.GREEN);


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        viewport2 = graph2.getViewport();
        viewport2.setScrollable(true);
        viewport2.setXAxisBoundsManual(true);
        series2X.setColor(Color.RED);
        series2Y.setColor(Color.BLACK);
        series2Z.setColor(Color.GREEN);


        Button btn = findViewById(R.id.startButton);
        Button btn2 = findViewById(R.id.pauseButton);
        Button btn3 = findViewById(R.id.restartButton);

        // Start the graph
        btn.setOnClickListener (v -> {
            graph.addSeries(seriesX);
            graph.addSeries(seriesY);
            graph.addSeries(seriesZ);
            graph2.addSeries(series2X);
            graph2.addSeries(series2Y);
            graph2.addSeries(series2Z);

        });

        // Pause the graph

        btn2.setOnClickListener(v -> {

        });

        // Restart the graph
        btn3.setOnClickListener(v -> {
            graph.removeAllSeries();
            graph.addSeries(seriesX);
            graph.addSeries(seriesY);
            graph.addSeries(seriesZ);
            graph2.addSeries(series2X);
            graph2.addSeries(series2Y);
            graph2.addSeries(series2Z);
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