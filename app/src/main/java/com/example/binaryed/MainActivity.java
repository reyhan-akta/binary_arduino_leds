package com.example.binaryed;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView_1;
    ImageView imageView_2;
    ImageView imageView_3;
    ImageView imageView_4;
    ImageView imageView_5;
    ImageView imageView_6;
    ImageView imageView_7;
    ImageView imageView_8;
    ImageView imageView_9;
    ImageView imageView_10;

    ImageView turn_all_leds_off;

    String binaryString;

    char[][] led_status = {
            {'1', '2','3','4'},
            {'f', 's','t','d'}
    };



    String completedbinary;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothAdapter bt_adapter;
    BluetoothDevice hc05;
    BluetoothSocket bt_socket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView_1 = findViewById(R.id.imageView9);
        imageView_2 = findViewById(R.id.imageView11);
        imageView_3 = findViewById(R.id.imageView12);
        imageView_4 = findViewById(R.id.imageView13);
        imageView_5 = findViewById(R.id.imageView14);
        imageView_6 = findViewById(R.id.imageView15);
        imageView_7 = findViewById(R.id.imageView16);
        imageView_8 = findViewById(R.id.imageView17);
        imageView_9 = findViewById(R.id.imageView18);
        imageView_10 = findViewById(R.id.imageView20);

        turn_all_leds_off = findViewById(R.id.turnalloff);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView_1.setOnClickListener(this);
        imageView_2.setOnClickListener(this);
        imageView_3.setOnClickListener(this);
        imageView_4.setOnClickListener(this);
        imageView_5.setOnClickListener(this);
        imageView_6.setOnClickListener(this);
        imageView_7.setOnClickListener(this);
        imageView_8.setOnClickListener(this);
        imageView_9.setOnClickListener(this);
        imageView_10.setOnClickListener(this);
        turn_all_leds_off.setOnClickListener(this);


        bt_adapter = BluetoothAdapter.getDefaultAdapter();

        hc05 = bt_adapter.getRemoteDevice("00:23:00:00:6B:DD");


        do{
            try{
                bt_socket = hc05.createRfcommSocketToServiceRecord(MY_UUID);
                bt_socket.connect();
            }catch (IOException e){
                System.out.println("there is an error " + e);
            }
        }while (!bt_socket.isConnected());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView9:
                 binaryString = Integer.toBinaryString(1);
                 blinkTheLeds(completeToFourDigit(binaryString));
                 break;
            case R.id.imageView11:
                 binaryString = Integer.toBinaryString(2);
                 blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView12:
                 binaryString = Integer.toBinaryString(3);
                 blinkTheLeds(completeToFourDigit(binaryString));
                 break;
            case R.id.imageView13:
                binaryString = Integer.toBinaryString(4);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView14:
                binaryString = Integer.toBinaryString(5);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView15:
                binaryString = Integer.toBinaryString(6);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView16:
                binaryString = Integer.toBinaryString(7);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView17:
                binaryString = Integer.toBinaryString(8);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView18:
                binaryString = Integer.toBinaryString(9);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.imageView20:
                binaryString = Integer.toBinaryString(10);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;
            case R.id.turnalloff:
                binaryString = Integer.toBinaryString(0);
                blinkTheLeds(completeToFourDigit(binaryString));
                break;

        }
    }

    String completeToFourDigit(String binary_string){
        completedbinary = "";
        for(int i=0;i< 4- binary_string.length();i++){
             completedbinary += '0';
        }
        return  completedbinary + binary_string;

    }

    void blinkTheLeds(String four_digit){
        for(int i=0;i<four_digit.length();i++){

            int digit = Integer.parseInt(String.valueOf(four_digit.charAt(i)));
            sendSignal(led_status[digit][i]);

        }
    }

    void sendSignal(char signal){

        try {
            OutputStream outputStream = bt_socket.getOutputStream();
            outputStream.write(signal);
        }catch (IOException e){

        }
    }
}

