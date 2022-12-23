package com.mygame.theroadmusttaken.Manegement;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.mygame.theroadmusttaken.Protocol.CallBack_StepsProtocol;

public class StepDetector {



    private SensorManager sensorManager;
    private Sensor sensor;

    int timeStamp = 0;

    private CallBack_StepsProtocol callBack_stepsProtocol;

    /**
     * Step detector constructor
     * @param context the context of the activity or application or service
     */
    public StepDetector(Context context, CallBack_StepsProtocol _callBack_stepsProtocol) {
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_stepsProtocol = _callBack_stepsProtocol;
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
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.smallLeftStep();
                }
            }
        }

        if (x < -9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.bigLeftStep();
                }
            }
        }

        if (x > 6.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.smallRightStep();
                }
            }
        }

        if (x > 9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.bigRightStep();
                }
            }
        }

        if (y < -6.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.lowerStep();
                }
            }
        }

        if (y < -9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.lowestStep();
                }
            }
        }

        if (y > 6.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.fasterStep();
                }
            }
        }

        if (y > 9.0) {
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = (int) System.currentTimeMillis();
                if (callBack_stepsProtocol != null) {
                    callBack_stepsProtocol.fastestStep();
                }
            }
        }
    }

}
