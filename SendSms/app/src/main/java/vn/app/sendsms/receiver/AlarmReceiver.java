package vn.app.sendsms.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import vn.app.sendsms.activity.MainActivity;
import vn.app.sendsms.database.MessageEntity;
import vn.app.sendsms.model.Message;

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

            wakeLock();

            MessageEntity messageEntity = new MessageEntity(context);
            ArrayList<Message> listMessage = messageEntity.getMessageSend();
            for (Message message : listMessage){
                Log.d(TAG, message.getIdServer()+" "+message.getContentSms()+" "+message.getDateSend()+" "+message.getNumberReceiver()+" "+message.getSendFlag());
                sendSms(context, message.getNumberReceiver(), message.getContentSms());
                message.setSendFlag(1);
                messageEntity.update(message);
            }

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

    private void sendSms(Context context, String phoneNumber, String message){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
