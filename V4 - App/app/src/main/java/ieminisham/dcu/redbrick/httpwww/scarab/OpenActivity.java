package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;


import java.io.File;

public class OpenActivity extends AppCompatActivity {

    String chosenFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        String MY_FILE_NAME = Environment.getExternalStorageDirectory().getPath() + "/Scarab_Notes/";
        File folder = new File(MY_FILE_NAME);


        if(!folder.exists())
        {
            folder.mkdir();
        }
        String[] listOfFiles = folder.list();
        if(listOfFiles.length <1)
        {
            TextView textView = (TextView) findViewById(R.id.noFiles);
            textView.setText("No files Available");
        }

        else
        {
            //create listview
            ListView mainListView = (ListView) findViewById(R.id.mainListView);
            //create listener for each link in list
            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                //on item click create a url and open it in the browser
                public void onItemClick(AdapterView<?> l, View v, int position, long id) {
                    String file =(String) l.getItemAtPosition(position);
                    chosenFile = file;
                    viewFile(file);
                }
            });

            linkArrayAdaptor listAdapter = new linkArrayAdaptor(this, listOfFiles);
            mainListView.setAdapter(listAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void viewFile(String file)
    {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("fileKey", chosenFile);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }
}
