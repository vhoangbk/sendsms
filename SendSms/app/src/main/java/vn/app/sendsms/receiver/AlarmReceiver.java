package vn.app.sendsms.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * start socket service
 * check Sync database
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    public static final String ACTION_ALARM = "vn.app.sendsms.alarm";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "action "+intent.getAction());
        if (ACTION_ALARM.equalsIgnoreCase(intent.getAction())){
            this.mContext = context;

            //check current == setting time
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            wakeLock();

        }
    }
    private void wakeLock(){
        Log.d(TAG, ">>>> wakeLock");
        PowerManager.WakeLock wakeLock = null;
        WifiManager.WifiLock wifiLock = null;
        try {
            PowerManager pm = (PowerManager) mContext
                    .getSystemService(Context.POWER_SERVICE);
            // acquire a WakeLock to keep the CPU running
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakeLock");
            if(!wakeLock.isHeld()){
                wakeLock.acquire();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
