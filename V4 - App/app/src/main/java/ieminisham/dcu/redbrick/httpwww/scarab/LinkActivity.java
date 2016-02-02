package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class LinkActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        Intent searchIntent = getIntent();
        String [] linkArray = searchIntent.getStringArrayExtra("linkKey");

        TextView linkView = (TextView) findViewById(R.id.linkCollection);
        linkView.setMovementMethod(new ScrollingMovementMethod());
        linkView.setTextSize(15);

        for(int i =0; i < linkArray.length; i++)
        {
            String tmp = "<a href =\"https://en.wikipedia.org" + linkArray[i] +"\">" + linkArray[i] + "</a>\n";
            linkView.append(tmp);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_out_top, R.anim.pull_in_bottom);
    }



}