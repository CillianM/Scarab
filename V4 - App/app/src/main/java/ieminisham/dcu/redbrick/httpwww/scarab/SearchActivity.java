package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<String> links;
    String searchedURL;
    EditText globalEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        Intent searchIntent = getIntent();
        String input = searchIntent.getStringExtra("searchKey");

        final EditText textView = (EditText) findViewById(R.id.content);
        textView.setTextSize(10);
        Parser parse = new Parser(input,textView);
        links = parse.search();
        searchedURL = input;
        globalEditText = textView;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Go To Source");
        menu.add(0,2,0,"Save");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == 1)
        {
            searchedURL = "https://en.wikipedia.org/wiki/" + searchedURL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(searchedURL));
            startActivity(i);
        }

        if(id == 2)
        {
            try
            {
                String MY_FILE_NAME = Environment.getExternalStorageDirectory().getPath() + "/Scarab_Notes/" + searchedURL+ ".txt";

                if(!new File(Environment.getExternalStorageDirectory().getPath() + "/Scarab_Notes/").exists())
                {
                    File newDir = new File(Environment.getExternalStorageDirectory().getPath() + "/Scarab_Notes/");
                    newDir.mkdir();
                }
                PrintWriter writer = new PrintWriter(MY_FILE_NAME);
                writer.println(globalEditText.getText().toString());
                writer.close();

                //display file saved message
                Toast.makeText(getBaseContext(), searchedURL + ".txt" + " saved successfully in " + Environment.getExternalStorageDirectory().getPath()+ "/Scarab_Notes/",
                        Toast.LENGTH_SHORT).show();
            }

            catch (IOException e)
            {
                // put notification here later!!!
                e.printStackTrace();
            }
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
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
