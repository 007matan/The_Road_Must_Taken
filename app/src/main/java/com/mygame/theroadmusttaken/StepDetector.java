package com.mygame.theroadmusttaken;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepDetector {

    public interface CallBack_steps {
        void smallRightStep();
        void bigRightStep();
        void smallLeftStep();
        void bigLeftStep();
    }

    private SensorManager sensorManager;
    private Sensor sensor;

    int timeStamp = 0;

    private CallBack_steps callBack_steps;

    /**
     * Step detector constructor
     * @param context the context of the activity or application or service
     */
    public StepDetector(Context context, CallBack_steps _callBack_steps) {
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_steps = _callBack_steps;
    }


    public void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            calculateStep(x, y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateStep(float x, float y) {
        if (x < -6.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_steps != null) {
                    callBack_steps.smallLeftStep();
                }
            }
        }

        if (x < -9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_steps != null) {
                    callBack_steps.bigLeftStep();
                }
            }
        }

        if (x > 6.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_steps != null) {
                    callBack_steps.smallRightStep();
                }
            }
        }

        if (x > 9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_steps != null) {
                    callBack_steps.bigRightStep();
                }
            }
        }
    }

}
