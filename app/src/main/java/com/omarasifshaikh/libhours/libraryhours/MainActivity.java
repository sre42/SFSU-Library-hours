package com.omarasifshaikh.libhours.libraryhours;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.omarasifshaikh.libhours.libraryhours.model.Flower;
import com.omarasifshaikh.libhours.libraryhours.model.LibraryEntry;
import com.omarasifshaikh.libhours.libraryhours.parsers.FlowerJSONParser;
import com.omarasifshaikh.libhours.libraryhours.parsers.LibraryEntryJSONParser;

import java.util.ArrayList;
import java.util.List;

//import com.importio.api.clientlite.ImportIO;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "libApp";
    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

    List<Flower> flowerList;
    List<LibraryEntry> libraryEntryList;
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
            //requestData("http://services.hanselandpetal.com/feeds/flowers.json");
                //This is for lib exceptions
            //requestData("https://api.import.io/store/data/cc2c7a71-e4e8-4937-a060-47d6d42345c1/_query?input/webpage/url=http%3A%2F%2Fwww.library.sfsu.edu%2Fabout%2Fhours.php&_user=a0be377d-3b77-4d38-8437-cb369a049f73&_apikey=a0be377d-3b77-4d38-8437-cb369a049f73%3AD934631gu6bmL1Xw4QS7iDFiXRszMaQQIwNaYJWV8eRSflFYIh%2FVZ5DGo2LD95%2FQScOeSmyfdqdgXBEQqS5QCA%3D%3D");
                //This is for lib hours
            requestData("https://api.import.io/store/data/d4eccf55-3e6d-475b-ab6e-e30f282a040f/_query?input/webpage/url=http%3A%2F%2Fwww.library.sfsu.edu%2Fabout%2Fhours.php&_user=a0be377d-3b77-4d38-8437-cb369a049f73&_apikey=a0be377d-3b77-4d38-8437-cb369a049f73%3AD934631gu6bmL1Xw4QS7iDFiXRszMaQQIwNaYJWV8eRSflFYIh%2FVZ5DGo2LD95%2FQScOeSmyfdqdgXBEQqS5QCA%3D%3D");
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

    protected void updateDisplay() {
        if(flowerList!=null){
            for(Flower flower : flowerList) {
                output.append(flower.getName() + "\n");
            }
        }
    }
    protected void updateDisplay2() {
        if(libraryEntryList!=null){
            Log.d(TAG,"inside UpdateDisplay2");
            for(LibraryEntry libraryEntry : libraryEntryList) {
                output.append(libraryEntry.getId() + "\n");
                output.append(libraryEntry.getName() + "\n");
            }
        }
    }
    protected void updateDisplay3(String message) {
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
            //updateDisplay("starting class");
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
            //flowerList = FlowerJSONParser.parseFeed(s);
            libraryEntryList = LibraryEntryJSONParser.parseFeed(s);
            Log.d(TAG,"Done with parsing...");


            updateDisplay2();
            //updateDisplay3(s);
            //pb.setVisibility(View.INVISIBLE);

            tasks.remove(this);
            if(tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //updateDisplay(values[0]);
        }
    }
}
