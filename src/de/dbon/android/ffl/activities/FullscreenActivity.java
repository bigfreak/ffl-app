
package de.dbon.android.ffl.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import de.dbon.android.ffl.ImageLoader;
import de.dbon.android.ffl.data.CurrentArticle;
import de.dbon.dev.ffl.R;

public class FullscreenActivity extends Activity {

    private ImageView fullscreenview;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);        

        fullscreenview = (ImageView) findViewById(R.id.fullimage);
        imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(CurrentArticle.imgUrl, fullscreenview);

        fullscreenview.setOnClickListener(new OnClickListener() {

            @Override
			public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fullscreen, menu);
        return true;
    }

}
