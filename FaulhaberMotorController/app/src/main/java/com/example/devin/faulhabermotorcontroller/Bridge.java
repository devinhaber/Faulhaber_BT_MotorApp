package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;

/**
 * Created by Devin on 6/27/2015.
 */
public class Bridge {
    public static BluetoothSocket socket = null;

    public static BluetoothSocket getSocket(){
        return socket;
    }

    public static void updateSocket(BluetoothSocket Socket){
        socket = Socket;
    }
}
