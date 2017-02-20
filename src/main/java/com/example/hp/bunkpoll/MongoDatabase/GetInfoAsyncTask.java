package com.example.hp.bunkpoll.MongoDatabase;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.hp.bunkpoll.LoginQuery;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Created by HP on 2/17/2017.
 */

public class GetInfoAsyncTask extends AsyncTask<String, Void, Boolean> {

public static final String LOG_TAG=GetInfoAsyncTask.class.getSimpleName();
    static String server_output = null;
    static String temp_output = null;

    public URL createUrl(){
        URL url=null;

        QueryBuilder qb=new QueryBuilder();
        try {
            url=new URL(qb.buildInfoGetURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String makkeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";
                if(url==null)
                {
                    return jsonResponse;
                }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
try{

        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
       // urlConnection.setRequestProperty("Accept", "application/json");

        if (urlConnection.getResponseCode() == 200) {
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } else {
            Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }

    } catch (IOException e) {
        Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
    } finally {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    return jsonResponse;
}

public String readFromStream(InputStream inputStream)throws IOException
{
    StringBuilder output=new StringBuilder();
    if(inputStream!=null)
    {
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader=new BufferedReader(inputStreamReader);
        String line=reader.readLine();
        while(line!=null)
        {
            output.append(line);
            line=reader.readLine();
        }
    }
    return output.toString();
}

    public ArrayList<LoginQuery> extractFeatureFromJson(String jsonQueryResponse)
    {
        if(TextUtils.isEmpty(jsonQueryResponse))
        {
                return null;
        }
        try {
            JSONArray baseJsonResponse = new JSONArray(jsonQueryResponse);
            ArrayList<LoginQuery>lq=new ArrayList<>();

            for (int i = 0; i < baseJsonResponse.length(); i++) {
                JSONObject obj = baseJsonResponse.getJSONObject(i);
                String email = obj.getString("email");
                String pass = obj.getString("pass");
                lq.add(new LoginQuery(email, pass));

            }
            return lq;
        }
         catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;


}


    @Override
    protected Boolean doInBackground(String... arg) {

        URL url =createUrl();
        String jsonResponse=null;
        try {
            jsonResponse = makkeHttpRequest(url);
        }catch(IOException e)
        {
            e.printStackTrace();;
        }

        ArrayList<LoginQuery> lq=extractFeatureFromJson(jsonResponse);
        for(int i=0;i<lq.size();i++) {
            if (arg[0].matches(lq.get(i).getQueryEmail()) && arg[1].matches(lq.get(i).getQueryPass())) {
                return true;
            }
        }
/**
        try {
            QueryBuilder qb = new QueryBuilder();
            URL url = new URL(qb.buildInfoGetURL());
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((temp_output = br.readLine()) != null) {
                server_output = temp_output;
            }

            // create a basic db list
            String mongoarray = "{ artificial_basicdb_list: " + server_output + "}";
            Object o = com.mongodb.util.JSON.parse(mongoarray);


              BasicDBObject dbObj = (BasicDBObject) o;
            BasicDBList contacts = (BasicDBList) dbObj.get("artificial_basicdb_list");


            for (Object obj : contacts) {
                BasicDBObject userObj = (BasicDBObject) obj;

                LoginQuery lq=new LoginQuery();


                lq.setQueryEmail(userObj.get("email").toString());
                lq.setQueryPass(userObj.get("pass").toString());

                if(arg[0]==lq.getQueryEmail() && arg[1]==lq.getQueryPass())
                {
                    return true;
                }



            }
        } catch (Exception e) {
            e.getMessage();
        }

**/
        return false;
    }

}
