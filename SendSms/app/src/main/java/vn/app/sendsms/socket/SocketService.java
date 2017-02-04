package vn.app.sendsms.socket;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import vn.app.sendsms.database.MessageEntity;
import vn.app.sendsms.model.Message;
import vn.app.sendsms.network.APIClient;
import vn.app.sendsms.network.APIClientListener;
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
    public int onStartCommand(Intent intent, final int flags, int startId) {

        mSocket.on(Constant.ON_SERVER_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, new String(args[0].toString()));

                String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                String urlWakeup = APIClient.URL_WAKEUP+"?"+Constant.KEY_PARAM_DEVICE_ID+"="+androidId;
                APIClient.getInstance().stringGetRequest(SocketService.this, urlWakeup, false, false, new APIClientListener() {
                    @Override
                    public void onSuccess(JSONObject success) {
                        Log.d(TAG, "onSuccess");

                        try {
                            JSONObject data = success.getJSONObject("data");
                            String id = data.getString("id");
                            String dateSend = data.getString("date_send");
                            String contentSms = data.getString("content_sms");
                            String numberReceiver = data.getString("number_receiver");

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date date = formatter.parse(dateSend);

                            Message message = new Message(id, contentSms, date.getTime(), numberReceiver);
                            MessageEntity messageEntity = new MessageEntity(SocketService.this);
                            messageEntity.add(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(JSONObject error) {
                        Log.d(TAG, "onError");
                    }
                });
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
