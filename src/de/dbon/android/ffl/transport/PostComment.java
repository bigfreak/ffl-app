package de.dbon.android.ffl.transport;

import android.app.Activity;

public class PostComment extends FFLJsonRequest{

    private String news_id;
    private String author;
    private String content;

    public PostComment(Activity a, String news_id, String author, String content) {
        super(a);
        this.a = a;
        this.news_id = news_id;
        this.author = author;
        this.content = content;
    }

    @Override
    protected String doInBackground(Void... params) {
        String[] auth = {"auth", "authcode"};
        String[] action = {"action", "post_comment"};
        String[] news_id = {"news_id", this.news_id.toString()};
        String[] author = {"author", this.author};
        String[] content = {"content", this.content};
        String[][] parameters = {auth, action, news_id, author, content};
        return fireRequest(parameters);
      
    }

   
}
