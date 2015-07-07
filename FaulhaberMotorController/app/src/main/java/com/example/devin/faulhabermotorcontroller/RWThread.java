package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Devin on 6/27/2015.
 */
public class RWThread extends Thread {

    BluetoothSocket socket;
    BufferedInputStream inStream;
    BufferedOutputStream outStream;

    public RWThread(BluetoothSocket socket){
        this.socket = socket;
        try{
            inStream = new BufferedInputStream(socket.getInputStream());
            outStream = new BufferedOutputStream(socket.getOutputStream());
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
            for(int i = 0; i < data.length;i++){
            System.out.println((char)data[i]);}
            outStream.write(data);
            outStream.write((byte) 13); //ASCII Carriage Return
            System.out.println((char) (byte) 13);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write Error");
        }


    }

    public void cancel(){
        try{
            outStream.close();
            inStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
