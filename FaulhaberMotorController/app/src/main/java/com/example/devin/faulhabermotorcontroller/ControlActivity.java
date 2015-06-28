package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ControlActivity extends ActionBarActivity {
    private BluetoothSocket socket;
    private RWThread rwthread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        this.socket =  Bridge.getSocket();
        rwthread = new RWThread(socket);
        rwthread.start();
    }

    private void writeData(byte[] data){
        rwthread.write(data);
    }

    public void setSpeed(View view){
        EditText editspeed =(EditText) findViewById(R.id.editspeed);
        String speed = editspeed.getText().toString();
        writeData((getString(R.string.setSpeed)+ speed).getBytes());
    }

    public void setPosition(View view){
        EditText editposition =(EditText) findViewById(R.id.editposition);
        String position = editposition.getText().toString();
        writeData((getString(R.string.setPosition) + position).getBytes());
    }

    public void changeSpeed(View view){
        String changespeed = "";
        if(view.getId() == R.id.speedup){
            changespeed = getString(R.string.changeSpeedUp);
        }
        else {
            changespeed = getString(R.string.changeSpeedDown);
        }
        writeData((changespeed + getString(R.string.changeSpeedAmount)).getBytes());
    }

    public void changePosition(View view){
        String changeposition = "";
        if(view.getId() == R.id.positionup){
            changeposition = getString(R.string.changePositionUp);
        }
        else {
            changeposition = getString(R.string.changePositionDown);
        }
        writeData((changeposition + getString(R.string.changePositionAmount)).getBytes());
    }

    public void stop(View view){
        writeData(getString(R.string.stop).getBytes());
    }
    public void returnToMain(View view){
        Intent homeintent = new Intent(this, MainActivity.class);
        startActivity(homeintent);
        rwthread.cancel();
        finish();
        return;
    }

}
