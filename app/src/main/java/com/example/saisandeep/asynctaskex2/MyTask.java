package com.example.saisandeep.asynctaskex2;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by saisandeep on 3/11/2015.
 */
class MyTask extends AsyncTask<String,Integer,Boolean>
{

    private int contentLength=-1;
    private int counter=0;
    private int calculatedProgress=0;
     Activity activity;

    public MyTask(Activity activity){

        onAttach(activity);
    }

    public void onAttach(Activity activity){

        this.activity=activity;
    }

    public void onDetach(){

        activity=null;
    }


    @Override
    protected void onPreExecute() {

        if(activity!=null) {


            ((MyActivity) activity).showProgressBarBeforeDownloading();

        }

        //progressBar.setVisibility(View.VISIBLE);

           /* if(MyActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {

                MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
               //in video used Configuration.ORIENTATION_LANDSCAPE But this is showing an error.

            }
            else
            {

                MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }*/
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

        if(activity!=null) {
            calculatedProgress = (int) (((double) values[0] / contentLength) * 100);
            ((MyActivity) activity).updateProgress(calculatedProgress);
        }
        else{
            Log.d("Sandeep","Skipping progress update since activity is null");
        }
        //progressBar.setProgress(calculatedProgress);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if(activity!=null) {

            ((MyActivity) activity).hideProgressBarAfterDownLoading();

        }
        //progressBar.setVisibility(View.GONE);
        // MyActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //REMOVES THE LOCK AND ACTIVATES THE SCREEN ORENTATION SENSOR AGAIN
    }
}
