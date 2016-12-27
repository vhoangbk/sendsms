package vn.app.sendsms.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import vn.app.sendsms.R;
import vn.app.sendsms.SendSmsApplication;
import vn.app.sendsms.utils.Constant;
import vn.app.sendsms.utils.Utils;


/**
 * @author : Archi-Edge
 * @copyright © 2016 Repica. All rights reserved.
 */
public class APIClient {

    public static final String URL_BASE = Constant.SERVER_URL + "/api/";

    public static final String URL_LOGIN = URL_BASE + "login";

    public static final String TAG = APIClient.class.getSimpleName();

    private static APIClient mInstance;

    private ProgressDialog progress;
    private Context mContext;

    private APIClient() {
    }

    public static APIClient getInstance() {
        if (mInstance == null) {
            mInstance = new APIClient();
        }
        return mInstance;
    }

    private void showDialog() {
        if (mContext == null){
            return;
        }
        if (progress.isShowing()){
            return;
        }
        progress = ProgressDialog.show(mContext, "",
                "", true);
    }

    private void dismissDialog() {
        if (mContext == null){
            return;
        }
        if (progress.isShowing()){
            progress.dismiss();
        }

    }

    private void jsonRequest(int method, String url, JSONObject params, com.android.volley.Response.Listener listener, com.android.volley.Response.ErrorListener error, String tag) {
        JsonObjectRequest jsonReq = new JsonObjectRequest(method, url, params, listener, error);
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                Constant.API_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SendSmsApplication.getInstance().addToRequestQueue(jsonReq, tag);
    }
    

    public void makeJsonRequestWithCallBackAndTag(Context context, String tag, int method, final String url, JSONObject params, final boolean showDialog, final boolean showAlert, final APIClientListener callBack) {

        this.mContext = context;

        Log.d(TAG, "[REQUEST: URL " + url + "] \n" + params);

        if (showAlert) {
            if (Utils.isOnline(mContext) == false) {
                callBack.onError(new JSONObject());
                Utils.showAlertInfo(mContext, mContext.getString(R.string.error_net_work));
                return;
            }
        }

        if (showDialog) {
            showDialog();
        }

        com.android.volley.Response.Listener successListener = new com.android.volley.Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "[RESPONSE: URL " + url + "] \n " + response);
                if (showDialog) {
                    dismissDialog();
                }
                if (response instanceof JSONObject) {
                    final JSONObject jsonObj = (JSONObject) response;
                    try {
                        int result = jsonObj.getInt("result");
                        if (result == 0) {
                            //success
                            callBack.onSuccess(jsonObj);
                        } else {
                            //error

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //error data
                        callBack.onError(jsonObj);
                        if (showAlert) {
                            Utils.showAlertInfo(mContext, mContext.getString(R.string.error_code_common));
                        }
                    }
                } else {
                    if (showAlert) {
                        Utils.showAlertInfo(mContext, mContext.getString(R.string.error_code_common));
                    }
                }

            }
        };

        com.android.volley.Response.ErrorListener errorListener = new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "[ERROR] " + url + " " + error);
                callBack.onError(new JSONObject());
                if (showDialog) {
                    dismissDialog();
                }

                //network error, time out, server error
                if (showAlert) {
                    Utils.showAlertInfo(mContext, mContext.getString(R.string.error_code_common));
                }
            }
        };

        jsonRequest(method, url, params, successListener, errorListener, tag);
    }
}
