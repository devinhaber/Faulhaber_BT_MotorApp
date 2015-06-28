package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Devin on 6/27/2015.
 */
public class RWThread extends Thread {

    BluetoothSocket socket;
    InputStream inStream;
    OutputStream outStream;

    public RWThread(BluetoothSocket socket){
        this.socket = socket;
        try{
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
        } catch (IOException e) {

        }

    }

    public void run(){
        int bytes;
        while(true){
            try {
                bytes = inStream.read();
            } catch (IOException e) {
                break;
            }
            //You can add reading here - for now nothing needs to be read
        }
    }

    public void write(byte[] data){
        try{
            outStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void cancel(){
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
