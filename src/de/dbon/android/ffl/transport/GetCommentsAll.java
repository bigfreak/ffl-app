
package de.dbon.android.ffl.transport;

import android.app.Activity;
import android.util.Log;

import de.dbon.android.fll.iface.ASyncDataCallback;

public class GetCommentsAll extends FFLJsonRequest {
    private final Activity a;

    public GetCommentsAll(Activity a) {
        super(a);
        this.a = a;
    }

    @Override
    protected String doInBackground(Void... params) {

        Log.w("ffl-app", "Fetching all comments");
        String[] auth = {
                "auth", "authcode"
        };
        String[] action = {
                "action", "comment_list_all"
        };

        String[][] parameters = {
                auth, action
        };

        return fireRequest(parameters);
    }

    @Override
    protected void onPostExecute(String result) {
        Log.w("ffl-app", "Done fetching comments");
        ((ASyncDataCallback) a).onDataReceived(result);
        super.onPostExecute(result);
    }
}
