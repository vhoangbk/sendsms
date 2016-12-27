package vn.app.sendsms.socket;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by hoangnv on 12/28/16.
 */

public class SocketService extends IntentService {

    private static final String TAG = "SocketService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SocketService() {
        super("SocketService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
    }
}
