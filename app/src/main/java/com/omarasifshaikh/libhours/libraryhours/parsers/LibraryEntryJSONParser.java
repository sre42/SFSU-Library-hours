package com.omarasifshaikh.libhours.libraryhours.parsers;

import android.util.Log;

import com.omarasifshaikh.libhours.libraryhours.model.LibraryEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 4/10/2015.
 */
public class LibraryEntryJSONParser {
    private static final String TAG = "libApp";
    public static List<LibraryEntry> parseFeed(String content) {
        try {
            JSONObject o1 = new JSONObject(content);
            String con1 = o1.getString("results");

            JSONArray ar = new JSONArray(con1);
            List<LibraryEntry> libraryEntryList = new ArrayList<>();
            int length = ar.length();
            int j=0;
            for (int i = 0; i < length; i++) {
                JSONObject obj = ar.getJSONObject(j);
                LibraryEntry libraryEntry = new LibraryEntry();
                libraryEntry.setId(i);
                libraryEntry.setName(obj.optString("column_1"));
                libraryEntry.setMon_thurs(obj.optString("mon_thurs"));
                libraryEntry.setFriday(obj.optString("friday"));
                libraryEntry.setSaturday(obj.optString("saturday"));
                libraryEntry.setSunday(obj.optString("sunday"));
                if(!obj.isNull("column_1")) {
                    libraryEntryList.add(libraryEntry);
                    j++;
                }
                else{
                    libraryEntry = null;
                    i--;
                    length--;
                    j++;
                }
                //Log.d(TAG,libraryEntry.getMon_thurs());
            }
            return libraryEntryList;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        }
}