package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastCPU extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String usage = intent.getExtras().getString("msg");
        Log.i("Recevie", usage);
    }

}
