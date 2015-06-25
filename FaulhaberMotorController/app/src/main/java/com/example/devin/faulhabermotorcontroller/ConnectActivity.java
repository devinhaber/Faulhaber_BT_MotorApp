package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ConnectActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void discoverBT(View view){
        BluetoothAdapter btadapter = BluetoothAdapter.getDefaultAdapter();
        //At this point we assume you can't reach this page without first enabling the BT adapter
        ListView list = new ListView(this);
        

    }


}
