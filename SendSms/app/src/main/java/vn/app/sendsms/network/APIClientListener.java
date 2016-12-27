package vn.app.sendsms.network;

import org.json.JSONObject;

/**
 * Created by hoangnv on 12/28/16.
 */

public interface APIClientListener {
    void onSuccess(JSONObject success);
    void onError(JSONObject error);
}
