package com.intpro.decepticons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent autostart_intent = new Intent(context, TheService.class);
        context.startService(autostart_intent);

        Log.i("Ambula Autostart", "started");
    }
}
