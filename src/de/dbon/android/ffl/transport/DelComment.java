
package de.dbon.android.ffl.transport;

import android.app.Activity;
import android.util.Log;
import de.dbon.android.fll.iface.ASyncDataCallback;

public class DelComment extends FFLJsonRequest {
    private final Activity a;
    private String commentId = null;   
    private int pos;

    public DelComment(Activity a, String commentId, int pos) {
        super(a);
        this.a = a;
        this.commentId = commentId;
        this.pos = pos;
    }

    @Override
    protected String doInBackground(Void... params) {

        
        String[] auth = {
                "auth", "authcode"
        };
        String[] action = {
                "action", "delete_comment"
        };
        String[] id = {
                "comments_id", commentId
        };

        String[][] parameters = {
                auth, action, id
        };        
        return fireRequest(parameters);        
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("ffl-app", "Deleting comment with ID: " + commentId);
        ((ASyncDataCallback) a).onDataReceived(result);
        super.onPostExecute(result);
    }
}
