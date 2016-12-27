package vn.app.sendsms.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import vn.app.sendsms.utils.Constant;

/**
 * receiver boot completed
 * wakeup alarm receiver
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final String TAG = "BootCompletedReceiver";
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private Context mContext;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "action "+intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(intent.getAction())){
            this.mContext = context;
            initAlarm();
            startAlarm();
        }
    }
    private void initAlarm() {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ACTION_ALARM);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

    }
    private void startAlarm() {
        Log.d(TAG, "startAlarm " + mAlarmManager);
        //30s emit action alarm reciver
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Constant.TIME_REPEAT_INTERVAL, Constant.TIME_REPEAT_INTERVAL, mPendingIntent);
    }
}
