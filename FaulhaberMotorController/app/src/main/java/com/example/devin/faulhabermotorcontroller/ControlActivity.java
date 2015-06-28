package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ControlActivity extends ActionBarActivity {
    private BluetoothSocket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        this.socket =  Bridge.getSocket();
    }

    private void returnToMain(){
        Intent homeintent = new Intent(this, MainActivity.class);
        startActivity(homeintent);
        finish();
        return;
    }

}
