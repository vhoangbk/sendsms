package vn.app.sendsms.receiver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

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

    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;

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
                sendSMS(context, message.getNumberReceiver(), message.getContentSms());
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

    public void sendSMS(Context context, String phoneNumber, String message) {
        PendingIntent piSent = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(context, 0,new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();

        int length = message.length();
        if(length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, messagelist, null, null);
        }
        else
            smsManager.sendTextMessage(phoneNumber, null, message, piSent, piDelivered);
    }
}
