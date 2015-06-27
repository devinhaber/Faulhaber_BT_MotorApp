package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import java.util.UUID;

import java.io.IOException;
import java.lang.Thread;
/**
 * Created by Devin on 6/27/2015.
 */
public class ConnectThread extends Thread {
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private final UUID my_UUID = UUID.fromString(""); //Actual UUID should be here
    private ConnectActivity context;

    public ConnectThread(BluetoothDevice device, ConnectActivity context) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket socket = null;
        this.context = context;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            socket = device.createRfcommSocketToServiceRecord(my_UUID);
        } catch (IOException e) { }

    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        adapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        context.manageConnectedSocket(socket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }


}

