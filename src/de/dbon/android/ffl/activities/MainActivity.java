
package de.dbon.android.ffl.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import de.dbon.android.ffl.NewsAdapter;
import de.dbon.android.ffl.data.CurrentArticle;
import de.dbon.android.ffl.transport.GetNews;
import de.dbon.android.fll.iface.ASyncDataCallback;
import de.dbon.dev.ffl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements ASyncDataCallback {

    public static boolean logged = false;

    JSONArray resultJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_main);
        setContentView(R.layout.refresher);
        new GetNews(this).execute();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (logged == true) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.activity_main_logged_on, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.activity_main_logged_off, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setIcon(R.drawable.ic_launcher);
                ad.setTitle(getResources().getString(R.string.about));
                ad.setMessage(Html.fromHtml(getResources().getText(
                        R.string.about_content).toString()));
                ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                ad.show();
                return true;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.administration:
                startActivity(new Intent(this, AdminActivity.class));
                return true;
                // Muss wieder entfernt werden 
            case R.id.verwaltung:
                startActivity(new Intent(this, AdminActivity.class));
                return true;
            case R.id.logoff:
                logged = false;
                return true;
            case R.id.refresh:
                setContentView(R.layout.refresher);
                new GetNews(this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDataReceived(String result) {
        setContentView(R.layout.activity_main);

        try {
            resultJSON = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NewsAdapter adapter = new NewsAdapter(this, resultJSON);

        ListView lv = (ListView) findViewById(R.id.list_news);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                JSONObject currentArticle;
                try {
                    currentArticle = resultJSON.getJSONObject(arg2);
                    CurrentArticle.id = currentArticle.getString("news_id");
                    CurrentArticle.headline = currentArticle.getString("headline");
                    CurrentArticle.content = currentArticle.getString("content");
                    CurrentArticle.imgUrl = "http://www.ffl-bergheim.de/upload/News/"
                            + currentArticle.getString("image").replaceAll(" ", "%20");
                    CurrentArticle.date = currentArticle.getString("datum");
                    CurrentArticle.author = currentArticle.getString("author");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(arg1.getContext(), ArticleActivity.class);
                startActivity(intent);
            }
        });
    }

}
