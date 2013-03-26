
package de.dbon.android.ffl.transport;

import android.app.Activity;
import android.util.Log;

import de.dbon.android.fll.iface.ASyncDataCallback;

public class GetComments extends FFLJsonRequest {
    private final Activity a;
    private String newsId = null;

    public GetComments(Activity a, String newsId) {
        super(a);
        this.a = a;
        this.newsId = newsId;
    }

    @Override
    protected String doInBackground(Void... params) {

        Log.w("ffl-app", "Fetching comments for news_id " + newsId);
        String[] auth = {
                "auth", "authcode"
        };
        String[] action = {
                "action", "comment_list"
        };
        String[] id = {
                "news_id", newsId
        };

        String[][] parameters = {
                auth, action, id
        };
        return fireRequest(parameters);

    }

    @Override
    protected void onPostExecute(String result) {
        Log.w("ffl-app", "Done fetching comments for news_id " + newsId);
        ((ASyncDataCallback) a).onDataReceived(result);
        super.onPostExecute(result);
    }
}
