
package de.dbon.android.ffl.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import de.dbon.dev.ffl.R;

public class AdminActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        
        Button comments_delete = (Button)findViewById(R.id.button_del_comment);
        comments_delete.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DeleteCommentActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_admin, menu);
        return true;
    }

}
