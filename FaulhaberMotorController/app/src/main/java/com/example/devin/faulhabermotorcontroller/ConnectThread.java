package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import java.io.IOException;
import java.lang.Thread;
/**
 * Created by Devin on 6/27/2015.
 */
public class ConnectThread extends Thread {
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectActivity context;

    public ConnectThread(BluetoothDevice device, ConnectActivity context) {

        socket = null;
        this.context = context;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            adapter.cancelDiscovery();

            Method m = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
            System.out.println(m.toString());
            socket = (BluetoothSocket) m.invoke(device, 1);
            System.out.println(socket.toString());
        }
            catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


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

