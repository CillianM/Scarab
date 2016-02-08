package ieminisham.dcu.redbrick.httpwww.scarab;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ViewActivity extends AppCompatActivity {

    EditText globalEditText;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);


        Intent searchIntent = getIntent();
        String filePath = searchIntent.getStringExtra("fileKey");
        setTitle(filePath);
        filePath = Environment.getExternalStorageDirectory().getPath() + "/Scarab_Notes/" + filePath;
        path = filePath;

        final EditText textView = (EditText) findViewById(R.id.fileContents);
        globalEditText = textView;
        textView.setTextSize(10);

        try
        {
            File file = new File(filePath);
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine())
            {
               textView.append(scan.nextLine() + "\n");
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Save");
        menu.add(0, 2, 0, "Delete");
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == 1) {
            try {

                PrintWriter writer = new PrintWriter(path);
                writer.println(globalEditText.getText().toString());
                writer.close();

                //display file saved message
                Toast.makeText(getBaseContext(), "File Saved!",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // put notification here later!!!
                e.printStackTrace();
            }
        }

        if(id == 2)
        {
            try
            {
                File file = new File(path);
                boolean bool = file.delete();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
            catch(Exception e)
            {

                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

}
