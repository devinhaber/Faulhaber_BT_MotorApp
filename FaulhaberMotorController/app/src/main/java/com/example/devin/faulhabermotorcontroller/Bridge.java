package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;

/**
 * Created by Devin on 6/27/2015.
 */
public class Bridge {
    //Exists to hold global var socket - did not wish to include static var in other classes
    public static BluetoothSocket socket = null;

    public static BluetoothSocket getSocket(){
        return socket;
    }

    public static void updateSocket(BluetoothSocket Socket){
        socket = Socket;
    }
}
