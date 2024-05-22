package com.example.mycricbtapplication;


import static com.example.mycricbtapplication.MainActivity.TAG;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Graph extends FragmentActivity {

    BluetoothAdapter mBlueAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    private String address = "34:86:5D:FD:62:CA";
    Button button1;
    UUID uuid;



    private StateViewModel model;
    private Handler handler;
    private Random random;
    Button button123;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        button123=findViewById(R.id.button22);


        model = new ViewModelProvider(this).get(StateViewModel.class);
        handler = new Handler();
        random = new Random();

        button123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment lineFragment = new LineFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.gauge_fragment, lineFragment);
                transaction.addToBackStack(null);
                transaction.commit();

//                Intent i = new Intent(getApplicationContext(),LineFragment.class);
//                startActivity(i);
            }
        });

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        mmDevice = mBlueAdapter.getRemoteDevice(address);
        uuid = mmDevice.getUuids()[0].getUuid();


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
                        Log.d(TAG, "AccX " + values[0] + "Accy " + values[1] + "AccZ " + values[2]+"\n"+"gyro " + values[3] + "gyro " + values[4] + "gyro " + values[5]+"\n"+"Temp: " + values[6]+"\n"+"audo: " + values[7]+"\n");
                        if (model != null) {

                            model.accX.setValue(Double.parseDouble(values[0]));
                            model.accY.setValue(Double.parseDouble(values[1]));
                            model.accZ.setValue(Double.parseDouble(values[2]));

                            model.gyroX.setValue(Double.parseDouble(values[3]));
                            model.gyroY.setValue(Double.parseDouble(values[4]));
                            model.gyroZ.setValue(Double.parseDouble(values[5]));

                            model.temperature.setValue(Double.parseDouble(values[6])); // temp

                            model.soundLiveM.setValue(Double.parseDouble(values[7]));//sound
                        }
                    }
                });
                Log.d(TAG, line);
            }
        }
    });








    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
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
            e.printStackTrace();
        }
    }
}