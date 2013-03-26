
package de.dbon.android.ffl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import de.dbon.android.ffl.transport.DelComment;
import de.dbon.dev.ffl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteCommentAdapter extends BaseAdapter {

    private Activity activity;
    private JSONArray data;
    private static LayoutInflater inflater = null;    

    public DeleteCommentAdapter(Activity a, JSONArray d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);     
    }

    @Override
	public int getCount() {
        return data.length();
    }

    @Override
	public Object getItem(int position) {
        return position;
    }

    @Override
	public long getItemId(int position) {
        return position;
    }

    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.entry_comment_del, null);

        TextView author = (TextView) vi.findViewById(R.id.del_comment_author);
        TextView content = (TextView) vi.findViewById(R.id.del_comment_content);        

        try {
            final JSONObject entry = data.getJSONObject(position);

            author.setText(entry.getString("author"));
            content.setText(entry.getString("content"));

            
            // Delete comment
            Button b = (Button) vi.findViewById(R.id.del_comment_button);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    int clickIndex = ((ListView)v.getParent().getParent()).getPositionForView((View) v.getParent());
                    String commentsId;
                    try {
                        commentsId = entry.getString("comments_id");
                        new DelComment(activity, commentsId, position).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    
                }
            });
           

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return vi;
    }
}
