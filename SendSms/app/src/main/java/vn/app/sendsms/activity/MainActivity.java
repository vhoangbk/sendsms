package vn.app.sendsms.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

import vn.app.sendsms.R;
import vn.app.sendsms.database.MessageEntity;
import vn.app.sendsms.model.Message;
import vn.app.sendsms.network.APIClient;
import vn.app.sendsms.network.APIClientListener;
import vn.app.sendsms.receiver.AlarmReceiver;
import vn.app.sendsms.socket.SocketService;
import vn.app.sendsms.utils.Constant;

/*
 * start socket service
 * start alarm manager
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = MainActivity.class.getSimpleName();

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);

        initAlarm();
        startAlarm();

        String androidId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String urlRegister = APIClient.URL_REGISTER+"?"+Constant.KEY_PARAM_DEVICE_ID+"="+androidId;

        APIClient.getInstance().stringGetRequest(MainActivity.this, urlRegister, false, false, new APIClientListener() {
            @Override
            public void onSuccess(JSONObject success) {
                Log.d(TAG, "onSuccess");
            }

            @Override
            public void onError(JSONObject error) {
                Log.d(TAG, "onError");
            }
        });

        MessageEntity messageEntity = new MessageEntity(MainActivity.this);
        ArrayList<Message> listMessage = messageEntity.getAll();
        for (Message message : listMessage){
            Log.d(TAG, message.getIdServer()+" "+message.getContentSms()+" "+message.getDateSend()+" "+message.getNumberReceiver()+" "+message.getSendFlag());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                start();
                break;
            case R.id.btnStop:
                stop();
                break;
        }
    }

    private void start(){
        startService(new Intent(MainActivity.this, SocketService.class));
    }

    private void stop(){
//        stopService(new Intent(MainActivity.this, SocketService.class));

//        MessageEntity messageEntity = new MessageEntity(MainActivity.this);
//        ArrayList<Message> listMessage = messageEntity.getMessageSend();
//        for (Message message : listMessage){
//            Log.d(TAG, message.getIdServer()+" "+message.getContentSms()+" "+message.getDateSend()+" "+message.getNumberReceiver()+" "+message.getSendFlag());
//        }
    }

    private void initAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ACTION_ALARM);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

    }
    private void startAlarm() {
        Log.d(TAG, "startAlarm ");
        //30s emit action alarm reciver
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Constant.TIME_REPEAT_INTERVAL, Constant.TIME_REPEAT_INTERVAL, mPendingIntent);
    }


}
