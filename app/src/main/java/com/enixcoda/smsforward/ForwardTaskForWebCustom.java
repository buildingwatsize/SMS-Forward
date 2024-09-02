package com.enixcoda.smsforward;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ForwardTaskForWebCustom extends AsyncTask<Void, Void, Void> {
    String TAG = "ForwardTaskForWebCustom";
    String templateBody;
    String senderNumber;
    String message;
    String endpoint;

    public ForwardTaskForWebCustom(String senderNumber, String message, String endpoint, String templateBody) {
        this.senderNumber = senderNumber;
        this.message = message;
        this.endpoint = endpoint;
        this.templateBody = templateBody;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            JSONObject body = new JSONObject();
            body.put("from", senderNumber);
            body.put("message", message);

            String payload = templateBody.replace("$msg", Utils.escape(body.toString()));
            Log.i(TAG, "sending: endpoint="+endpoint + " | payload=" + payload);
            TaskForWeb.httpRequest(endpoint, payload);
        } catch (IOException | JSONException e) {
            Log.d(Forwarder.class.toString(), e.toString());
        }
        return null;
    }

}