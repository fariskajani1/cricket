package com.example.mycricbtapplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class BTTestActivity extends AppCompatActivity  {
    private ListView listview;
    private ArrayList<String> item_list;
    private ArrayAdapter<String> adapter;

    private String address = "34:86:5D:FD:62:CA";
  //  private String address =  "C8:F0:9E:9E:11:1A";


    private TextView tv;
    BluetoothAdapter mBlueAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Button button1;
    UUID uuid;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bttest);



        tv = findViewById(R.id.textView1);
        listview = findViewById(R.id.list123);
        button1=findViewById(R.id.button1);
        item_list = new ArrayList<>();
        item_list.add("Tahir");

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                item_list
        );
        listview.setAdapter(adapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(BTTestActivity.this,Tabbed_Activity.class);
                startActivity(i);
            }
        });


        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
       mmDevice = mBlueAdapter.getRemoteDevice(address);
       uuid = mmDevice.getUuids()[0].getUuid();




    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        //  mmDevice = mBlueAdapter.getRemoteDevice("uuid");
        try {
            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            workerThread.start();

        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mmSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Thread workerThread = new Thread(new Runnable()
    {
        public void run()
        {
            Scanner scanner = new Scanner(mmInputStream);
            while(scanner.hasNextLine())
            {

                String line = scanner.nextLine();

                String[] values = line.split(";");


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        item_list.add("AccX: " + values[0] + "  Accy: " + values[1] + "  AccZ: " + values[2]+"\n"
                                +"gyro: " + values[3] + "  gyro: " + values[4] + "  gyro: " + values[5]+"\n"
                                + " Temp:  " + values[6]+"\n"
                                + " Sound: " + values[7]+"\n");
                        adapter.notifyDataSetChanged();
                     //   Collections.reverse(item_list);
                      //  listview.smoothScrollToPosition(0);
                    }
                });
                Log.d(MainActivity.TAG, line);
            }
        }
    });



}