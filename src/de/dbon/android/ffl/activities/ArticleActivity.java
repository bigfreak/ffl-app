
package de.dbon.android.ffl.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.dbon.android.ffl.ImageLoader;
import de.dbon.android.ffl.data.CurrentArticle;
import de.dbon.android.ffl.transport.GetComments;
import de.dbon.android.ffl.transport.PostComment;
import de.dbon.android.fll.iface.ASyncDataCallback;
import de.dbon.dev.ffl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleActivity extends Activity implements ASyncDataCallback {

    public static Integer news_id;

    ImageLoader imageLoader;
    LayoutInflater inflater;

    Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // get the comments
        new GetComments(this, CurrentArticle.id).execute();

        TextView headline = (TextView) findViewById(R.id.detailHeadline);
        TextView content = (TextView) findViewById(R.id.detailContent);
        TextView author = (TextView) findViewById(R.id.detailAuthor);
        TextView date = (TextView) findViewById(R.id.detailDate);
        ImageView img = (ImageView) findViewById(R.id.detailImage);
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FullscreenActivity.class));
            }
        });

        this.setTitle("FFL-Bergheim");
        headline.setText(CurrentArticle.headline);
        content.setText(CurrentArticle.content);
        author.setText(CurrentArticle.author);
        date.setText(CurrentArticle.date);
        imageLoader = new ImageLoader(this);
        if (CurrentArticle.imgUrl.equals("null")) {
            imageLoader.DisplayImage(null, img);
        } else {
            imageLoader.DisplayImage(CurrentArticle.imgUrl, img);
        }
    }

    @Override
    public void onDataReceived(String result) {
        JSONArray resultJSON = null;
        try {
            resultJSON = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.detailContentLayout);

        for (int i = 0; i < resultJSON.length(); i++) {
            try {
                JSONObject comment = resultJSON.getJSONObject(i);
                View vi = inflater.inflate(R.layout.entry_comment, null);
                TextView commentAuthor = (TextView) vi.findViewById(R.id.comment_author);
                TextView commentText = (TextView) vi.findViewById(R.id.comment_text);
                TextView commentMeta = (TextView) vi.findViewById(R.id.comment_meta);
                commentAuthor.setText(comment.getString("author"));
                commentMeta.setText(comment.getString("datum"));
                commentText.setText(comment.getString("content"));
                layout.addView(vi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        layout = (LinearLayout) findViewById(R.id.detailContentLayout);
        View vi = inflater.inflate(R.layout.post_comment, null);
        layout.addView(vi);

        Button b = (Button) findViewById(R.id.post_comment_button);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText post_comment_author = (EditText) findViewById(R.id.post_comment_author);
                EditText post_comment_content = (EditText) findViewById(R.id.post_comment_content);

                new PostComment(a, CurrentArticle.id, post_comment_author.getText().toString(),
                        post_comment_content.getText().toString()).execute();
                
                finish();
                startActivity(getIntent());
            }
        });

    }

}
