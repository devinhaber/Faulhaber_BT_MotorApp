package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;


public class ControlActivity extends ActionBarActivity {
    private BluetoothSocket socket;
    private RWThread rwthread;
    private int velocity;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        this.socket =  Bridge.getSocket();

        rwthread = new RWThread(socket);
        rwthread.start();
        rwthread.write("EN".getBytes());
        velocity = 0;
        pos = 0;
    }

    private void writeData(byte[] data){
        rwthread.write(data);
    }

    public void setSpeed(View view){
        EditText editspeed =(EditText) findViewById(R.id.editspeed);
        String speed = editspeed.getText().toString();

        try {
            velocity = Integer.parseInt(speed);
        } catch (NumberFormatException e){
            return;
        }

        writeData((getString(R.string.setSpeed) + velocity).getBytes());
    }

    public void setPosition(View view){
        stop(null);
        EditText editposition =(EditText) findViewById(R.id.editposition);
        String position = editposition.getText().toString();
        try {
            pos = Integer.parseInt(position);
        } catch (NumberFormatException e){
            return;
        }
        writeData((getString(R.string.setPosition) + position).getBytes());
        writeData("m".getBytes());
    }

    public void changeSpeed(View view){
        String changespeed = "";
        if(view.getId() == R.id.speedup){
            changespeed = getString(R.string.changeSpeedUp);
            velocity = velocity + Integer.parseInt(getString(R.string.changeSpeedAmount));
        }
        else {
            changespeed = getString(R.string.changeSpeedDown);
            velocity = velocity - Integer.parseInt(getString(R.string.changeSpeedAmount));
        }

        writeData((changespeed + velocity).getBytes());

    }

    public void changePosition(View view){
        stop(null);
        String changeposition = "";
        if(view.getId() == R.id.positionup){
            changeposition = getString(R.string.changePositionUp);
            pos = pos + Integer.parseInt(getString(R.string.changePositionAmount));
        }
        else {
            changeposition = getString(R.string.changePositionDown);
            pos = pos - Integer.parseInt(getString(R.string.changePositionAmount));
        }
        writeData((changeposition + pos).getBytes());
        writeData("m".getBytes());
    }

    public void stop(View view){
        velocity = 0;
        writeData(getString(R.string.stop).getBytes());
    }
    public void returnToMain(View view){
        stop(null);
        Intent homeintent = new Intent(this, MainActivity.class);
        startActivity(homeintent);
        rwthread.cancel();
        finish();
        return;
    }

    protected void onDestroy(){
        stop(null);
        rwthread.cancel();
        super.onDestroy();
    }

}
