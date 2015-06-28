package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;

import java.util.HashMap;


public class ConnectActivity extends ActionBarActivity {
    private ArrayAdapter arrayadapt;
    private HashMap<String, BluetoothDevice> devices;
    private boolean connecting;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.put(device.getAddress(), device);
                arrayadapt.add(device.getName() + "|" + device.getAddress());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayadapt = new ArrayAdapter(this, R.layout.basiclist);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(arrayadapt);
        devices = new HashMap<String, BluetoothDevice>();
        connecting = false;
        setContentView(R.layout.activity_connect);
    }

    public void discoverBT(View view){
        Button button = (Button) view;
        BluetoothAdapter btadapter = BluetoothAdapter.getDefaultAdapter();
        if(connecting){
            return;
        }
        if(button.getText().equals("Begin Discovery")) {


            //At this point we assume you can't reach this page without first enabling the BT adapter
            // Create a BroadcastReceiver for ACTION_FOUND
            // Register the BroadcastReceiver
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
            button.setText("Cancel Discovery");
        } else {
            btadapter.cancelDiscovery();
            button.setText("Begin Discovery");
            devices = new HashMap<String, BluetoothDevice>();
            arrayadapt = new ArrayAdapter(this, R.layout.basiclist);
        }




    }

    public void connect(View view){
        if(connecting){
            return;
        }
        BluetoothAdapter btadapter = BluetoothAdapter.getDefaultAdapter();
        LinearLayout line = (LinearLayout) view.getParent();
        String text = ((TextView) line.getChildAt(0)).getText().toString();
        String address = text.substring(text.indexOf("|") + 1);
        BluetoothDevice device = devices.get(address);
        btadapter.cancelDiscovery();
        new ConnectThread(device, this).run();
        connecting = true;


    }

    public void manageConnectedSocket(BluetoothSocket socket){
        connecting = false;
        Bridge.updateSocket(socket);
        Intent controlintent = new Intent(this, ControlActivity.class);
        startActivity(controlintent);
        finish();
    }

    public void back(View view){
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        Bridge.updateSocket(null);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
