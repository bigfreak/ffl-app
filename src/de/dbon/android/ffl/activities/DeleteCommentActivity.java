
package de.dbon.android.ffl.activities;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import de.dbon.android.ffl.DeleteCommentAdapter;
import de.dbon.android.ffl.transport.GetCommentsAll;
import de.dbon.android.fll.iface.ASyncDataCallback;
import de.dbon.dev.ffl.R;

public class DeleteCommentActivity extends Activity implements ASyncDataCallback {

    JSONArray resultJSON = null;
    DeleteCommentAdapter mAdapter;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.refresher);
        new GetCommentsAll(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_comment, menu);
        return true;
    }

    @Override
    public void onDataReceived(String result) {
        setContentView(R.layout.activity_delete_comment);
        ListView lv = (ListView) findViewById(R.id.list_comments_delete);
        try {
            resultJSON = new JSONArray(result);
            mAdapter = new DeleteCommentAdapter(this, resultJSON);
            lv.setAdapter(mAdapter);            
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
            startActivity(getIntent());
        }

    }

}
