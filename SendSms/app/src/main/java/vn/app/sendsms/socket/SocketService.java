package vn.app.sendsms.socket;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import vn.app.sendsms.SendSmsApplication;
import vn.app.sendsms.utils.Constant;

/**
 * Created by hoangnv on 12/28/16.
 */

public class SocketService extends Service {

    private static final String TAG = "SocketService";

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constant.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mSocket.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSocket.on(Constant.ON_SERVER_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, new String(args[0].toString()));
                sendSms("0123456789", new String(args[0].toString()));
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendSms(String phoneNumber, String message){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
