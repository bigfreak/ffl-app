
package de.dbon.android.ffl.transport;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public abstract class FFLJsonRequest extends AsyncTask<Void, Void, String> {

    Activity a;

    public FFLJsonRequest(Activity a) {
        this.a = a;
    }

    @Override
    protected abstract String doInBackground(Void... params);

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    protected String fireRequest(String[][] params) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.ffl-bergheim.de/backend.php");

        HttpResponse response = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.length);
        for (String[] param : params) {
            nameValuePairs.add(new BasicNameValuePair(param[0], param[1]));
        }

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d("ffl-app", "sending http request ...");
            response = httpclient.execute(httppost);
            Log.i("ffl-app", response.getStatusLine().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getStringFromStream(response);
    }

    protected String getStringFromStream(HttpResponse response) {
        String resp = null;
        InputStream inputStream;
        try {
            inputStream = response.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String bufferedStrChunk = null;
            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk);
            }
            resp = stringBuilder.toString();
            inputStream.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
