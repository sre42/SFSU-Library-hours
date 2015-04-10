package com.omarasifshaikh.libhours.libraryhours;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import com.importio.api.clientlite.ImportIO;

public class MainActivity extends ActionBarActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.textview);
        output.setMovementMethod(new ScrollingMovementMethod());
        //for(int i =0; i< 100; i++){
            //updateDisplay("line" + i);
        //}
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            if(isOnline()){
            requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
            }else{
                Toast.makeText(this,"Network isn't availible", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestData(String uri) {


        MyTask task = new MyTask();
        task.execute(uri);



        /*
        MyTask task = new MyTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "param1", "param2", "param3");
        */
    }

    protected void updateDisplay(String message) {
        output.append(message + "\n");
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
                if(netinfo != null && netinfo.isConnectedOrConnecting()){
                    return true;
                }
                else{
                    return false;
                }
    }
    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            updateDisplay("starting class");
            if(tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(s);
            //pb.setVisibility(View.INVISIBLE);

            tasks.remove(this);
            if(tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplay(values[0]);
        }
    }
}
