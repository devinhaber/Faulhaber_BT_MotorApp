package com.example.devin.faulhabermotorcontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public final static int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_connect) {
            BluetoothAdapter btadapter = BluetoothAdapter.getDefaultAdapter();
            if(btadapter == null){
                TextView textview = new TextView(this);
                textview.setTextSize(40);
                textview.setText("BlueTooth is NOT enabled");
                RelativeLayout relative =(RelativeLayout) findViewById(R.id.mainlayout);
                relative.addView(textview);

            } else {
                if(!btadapter.isEnabled()){
                    Intent enablebtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enablebtintent,REQUEST_ENABLE_BT);
                } else{
                    Intent connectintent = new Intent(this, ConnectActivity.class);
                    startActivity(connectintent);
                }
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == -1){
           Intent connectintent = new Intent(this, ConnectActivity.class);
           startActivity(connectintent);
       } else{
           return;
       }
    }
}
