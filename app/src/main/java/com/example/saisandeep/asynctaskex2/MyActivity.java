package com.example.saisandeep.asynctaskex2;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//is used because Async Task is kept in seperate class for use of AsyncTask even when there is orientation change
public class MyActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    EditText editText;
    ListView listView;
    String[] listofImages;
    ProgressBar progressBar;
    //LinearLayout loadingSection=null;
    UIFragment frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        editText= (EditText) findViewById(R.id.edit);
        listView= (ListView) findViewById(R.id.urlList);
        listView.setOnItemClickListener(this);
        listofImages=getResources().getStringArray(R.array.imageURLS);
        progressBar= (ProgressBar) findViewById(R.id.downloadProgress);

        if(savedInstanceState == null)
        {
            frag=new UIFragment();
            getSupportFragmentManager().beginTransaction().add(frag,"UIFragment").commit();


        }
        else
        {

            frag= (UIFragment) getSupportFragmentManager().findFragmentByTag("UIFragment");
        }


        if(frag!=null)
        {
            if(frag.task != null && frag.task.getStatus() == AsyncTask.Status.RUNNING)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void downloadImage(View v)
    {
        if(editText.getText().toString() != null && editText.getText().toString().length()>0)
        {

           // MyTask myTask=new MyTask();
            //myTask.execute(editText.getText().toString());

            frag.usebeginTasks(editText.getText().toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        editText.setText(listofImages[i]);
    }

    public void showProgressBarBeforeDownloading()
    {
        if(frag.task != null)
        {
            if(progressBar.getVisibility() != View.VISIBLE && progressBar.getProgress() != progressBar.getMax())
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgressBarAfterDownLoading()
    {
        if(frag.task != null)
        {
            if(progressBar.getVisibility() == View.VISIBLE)
            {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

   /*
    class MyTask extends AsyncTask<String,Integer,Boolean>

    {

        private int contentLength=-1;
        private int counter=0;
        private int calculatedProgress=0;

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);

            if(MyActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {

                MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
               //in video used Configuration.ORIENTATION_LANDSCAPE But this is showing an error.

            }
            else
            {

                MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean success=false;
            URL downloadurl=null;
            HttpURLConnection conn=null;
            InputStream ips=null;
            FileOutputStream fops=null;
            File file=null;
            try {
                downloadurl=new URL(params[0]);
                conn= (HttpURLConnection) downloadurl.openConnection();

                contentLength=conn.getContentLength();

                ips=conn.getInputStream();

                file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+" /"+ Uri.parse(params[0]).getLastPathSegment());
                fops=new FileOutputStream(file);
                //L.m(" "+file.getAbsolutePath());

                int read=-1;
                byte[] buffer=new byte[1024];

                while((read=ips.read(buffer))!=-1)
                {
                    fops.write(buffer,0,read);
                    counter=counter+read;
                    publishProgress(counter);
                    //L.m(" "+read);
                    // Log.d("Sandeep", "line is " + read);
                }
                success=true;

            } catch (MalformedURLException e) {

            } catch (IOException e) {

            }

            finally {


                if(conn!=null)
                {
                    conn.disconnect();
                }
                if(ips!=null)
                {
                    try {
                        ips.close();
                    } catch (IOException e) {

                    }
                }
                if(fops!=null)
                {
                    try {
                        fops.close();
                    } catch (IOException e) {

                    }
                }

            }

            return success;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            calculatedProgress=(int)(((double)values[0]/contentLength)*100);
            progressBar.setProgress(calculatedProgress);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            progressBar.setVisibility(View.GONE);
           // MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            //REMOVES THE LOCK AND ACTIVATES THE SCREEN ORENTATION SENSOR AGAIN
        }
    }*/
}
