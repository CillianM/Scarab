package ieminisham.dcu.redbrick.httpwww.scarab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;


public class LinkActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        //get rid of back button (this page is supposed to be an extension of the search page so the up arrow does just fine to convery this)
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        Intent searchIntent = getIntent();
        String[] linkArray = searchIntent.getStringArrayExtra("linkKey");

        //create listview
        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        //create listener for each link in list
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            //on item click create a url and open it in the browser
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {
            String chosenURL =(String) l.getItemAtPosition(position);
            chosenURL = "https://en.wikipedia.org" + chosenURL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(chosenURL));
            startActivity(i);
        }
        });

        linkArrayAdaptor listAdapter = new linkArrayAdaptor(this, linkArray);
        mainListView.setAdapter(listAdapter);

    }

    public void close(View view) {
        // Do something in response to button
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_bottom, R.anim.push_out_top);
    }


}