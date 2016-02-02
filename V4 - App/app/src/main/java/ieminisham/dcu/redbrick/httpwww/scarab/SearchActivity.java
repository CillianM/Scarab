package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<String> links;
    TextView globalView;
    private static final long serialVersionUID = 1L;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent searchIntent = getIntent();
        String input = searchIntent.getStringExtra("searchKey");

        TextView textView = (TextView) findViewById(R.id.content);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTextSize(10);
        Parser parse = new Parser(input,textView);
        links = parse.search();
        globalView = textView;

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
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void showLinks(View view) {
        // Do something in response to button
        String [] linkArray = new String [links.size()];
        for(int i =0; i < links.size(); i++)
        {
            linkArray[i] = links.get(i);
        }

        Intent linkIntent = new Intent(this, LinkActivity.class);
        linkIntent.putExtra("linkKey", linkArray);
        startActivity(linkIntent);
        overridePendingTransition(R.anim.pull_in_top, R.anim.push_out_bottom);
    }
}
