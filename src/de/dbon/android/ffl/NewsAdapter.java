
package de.dbon.android.ffl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.dbon.dev.ffl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsAdapter extends BaseAdapter {

    private Activity activity;
    private JSONArray data;
    private static LayoutInflater inflater = null;

    public ImageLoader imageLoader;

    public NewsAdapter(Activity a, JSONArray d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
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
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.entry_news, null);

        TextView heading = (TextView) vi.findViewById(R.id.heading);
        TextView subheading = (TextView) vi.findViewById(R.id.subheading);
        TextView anz_comments = (TextView) vi.findViewById(R.id.anz_comments);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.imageNews);

        try {
            JSONObject entry = data.getJSONObject(position);

            heading.setText(entry.getString("headline"));
            subheading.setText(entry.getString("datum") + " - " + entry.getString("author"));

            String plural = "e";
            if (Integer.parseInt(entry.getString("anzahl")) == 1) {
                plural = "";
            }
            anz_comments.setText(entry.getString("anzahl") + " Kommentar" + plural);

            Log.i("ffl-app", "http://www.ffl-bergheim.de/upload/News/"
                    + entry.getString("image").replaceAll(" ", "%20"));
            imageLoader.DisplayImage(
                    "http://www.ffl-bergheim.de/upload/News/"
                            + entry.getString("image").replaceAll(" ", "%20"), thumb_image);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return vi;
    }
}
