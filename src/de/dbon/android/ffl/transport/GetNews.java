
package de.dbon.android.ffl.transport;

import android.app.Activity;

import de.dbon.android.fll.iface.ASyncDataCallback;

public class GetNews extends FFLJsonRequest {

    private final Activity a;

    public GetNews(Activity a) {
        super(a);
        this.a = a;
    }

    @Override
    protected String doInBackground(Void... params) {
        String[] auth = {
                "auth", "authcode"
        };
        String[] action = {
                "action", "news_list"
        };
        String[][] parameters = {
                auth, action
        };
        return fireRequest(parameters);
    }

    @Override
    protected void onPostExecute(String result) {

        ((ASyncDataCallback) a).onDataReceived(result);
        super.onPostExecute(result);
    }
}
