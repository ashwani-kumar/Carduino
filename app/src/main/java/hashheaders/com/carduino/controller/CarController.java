package hashheaders.com.carduino.controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import hashheaders.com.carduino.view.CarControlsActivity;

/**
 * Created by Ashwani on 10-06-2017.
 */

public class CarController {
    private CarControlsActivity carControlsActivity;
    private BluetoothSocket btSocket;


    public CarController(CarControlsActivity carControlsActivity, BluetoothSocket btSocket) {
        this.carControlsActivity = carControlsActivity;
        this.btSocket = btSocket;
    }

    public void moveForward(){
        try
        {
            btSocket.getOutputStream().write("F".getBytes());
        }
        catch (IOException e)
        {
            carControlsActivity.msg("Error");
        }
    }

    public void moveBackwards(){
        try
        {
            btSocket.getOutputStream().write("B".getBytes());
        }
        catch (IOException e)
        {
            carControlsActivity.msg("Error");
        }
    }

    public void moveLeft(){
        try
        {
            btSocket.getOutputStream().write("L".getBytes());
        }
        catch (IOException e)
        {
            carControlsActivity.msg("Error");
        }
    }

    public void moveRight(){
        try {
            btSocket.getOutputStream().write("R".getBytes());
        }
        catch (IOException e) {
            carControlsActivity.msg("Error");
        }
    }

    public void disconnect(){
        if (btSocket!=null) //If the btSocket is busy{
            try {
                btSocket.close(); //close connection
            }
            catch (IOException e) {
                carControlsActivity.msg("Error");
            }
    }

    public void changeSpeed(int progress, boolean fromUser) {
        if (fromUser) {
            try {
                btSocket.getOutputStream().write(String.valueOf(progress).getBytes());
            } catch (IOException e) {
                Log.e(CarController.class.getSimpleName(), e.toString());
            }
        }
    }
}
