package cordova.plugin.aps.notification;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * This class echoes a string called from JavaScript.
 */
public class ApsNotification extends CordovaPlugin {
    CallbackContext _callbackContext;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCordovaIntent")) {
            getCordovaIntent(callbackContext);
            return true;
        }
        return false;
    }

    public boolean getCordovaIntent ( final CallbackContext context) {
        Intent intent = cordova.getActivity().getIntent();
        context.sendPluginResult(new PluginResult(PluginResult.Status.OK, intent.getStringExtra("payload")));
        return true;
    }
}
