package vn.app.sendsms.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

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

    public static final String URL_REGISTER = URL_BASE + "register";
    public static final String URL_WAKEUP = URL_BASE + "wakeup";

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
        if (progress != null && progress.isShowing()){
            return;
        }
        progress = ProgressDialog.show(mContext, "",
                "", true);
    }

    private void dismissDialog() {
        if (mContext == null){
            return;
        }
        if (progress != null && progress.isShowing()){
            progress.dismiss();
        }

    }

    private void stringRequest(int method, String url, com.android.volley.Response.Listener listener, com.android.volley.Response.ErrorListener error, String tag) {
        StringRequest stringRequest = new StringRequest(method, url, listener, error);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constant.API_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SendSmsApplication.getInstance().addToRequestQueue(stringRequest, tag);
    }
    

    public void stringGetRequest(Context context, final String url, final boolean isShowDialog, final boolean isShowAlert, final APIClientListener callBack) {

        this.mContext = context;

        Log.d(TAG, "[REQUEST] URL " + url);

        if (isShowAlert) {
            if (Utils.isOnline(mContext) == false) {
                callBack.onError(new JSONObject());
                Utils.showAlertInfo(mContext, mContext.getString(R.string.error_net_work));
                return;
            }
        }

        if (isShowDialog) {
            showDialog();
        }

        com.android.volley.Response.Listener successListener = new com.android.volley.Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "[RESPONSE] URL " + url + " \n " + response);
                if (isShowDialog) {
                    dismissDialog();
                }

                try {
                    JSONObject jsonObj = new JSONObject(response.toString());
                    boolean success = jsonObj.getBoolean("success");
                    if (success) {
                        //success
                        callBack.onSuccess(jsonObj);
                    } else {
                        callBack.onError(jsonObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onError(new JSONObject());
                    if (isShowAlert) {
                        Utils.showAlertInfo(mContext, mContext.getString(R.string.error_code_common));
                    }
                }

            }
        };

        com.android.volley.Response.ErrorListener errorListener = new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "[ERROR] " + url + " " + error);

                if (isShowDialog) {
                    dismissDialog();
                }
                //network error, time out, server error
                if (isShowAlert) {
                    Utils.showAlertInfo(mContext, mContext.getString(R.string.error_code_common));
                }
                callBack.onError(new JSONObject());
            }
        };
        stringRequest(Request.Method.GET, url, successListener, errorListener, "");

    }

}
