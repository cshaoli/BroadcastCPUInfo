package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastCPU extends BroadcastReceiver {



    private static final String TAG = "BroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receieve");

        String receiveInfo = intent.getExtras().getString("msg");
        Toast.makeText(context, "Broadcast.."+receiveInfo, Toast.LENGTH_SHORT).show();

    }

}
