package com.example.hp.bunkpoll.MongoDatabase;



import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.example.hp.bunkpoll.LoginQuery;
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

    public String makeHttpRequest(URL url) throws IOException
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

    urlConnection.setReadTimeout(10000 );
    urlConnection.setConnectTimeout(15000 );
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
        ArrayList<LoginQuery> lq = new ArrayList<LoginQuery>();
        try {
            JSONArray baseJsonResponse = new JSONArray(jsonQueryResponse);


            for (int i = 0; i < baseJsonResponse.length(); i++) {
                JSONObject obj = baseJsonResponse.getJSONObject(i);
                String email = obj.getString("email");
                String pass = obj.getString("pass");
                lq.add(new LoginQuery(email, pass));

            }
        }
         catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        }

        return lq;

}

    @Override
    protected Boolean doInBackground(String... arg) {

        URL url =createUrl();
        String jsonResponse=null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<LoginQuery> lq=extractFeatureFromJson(jsonResponse);
        int size=lq.size();
        for(int i=0;i<size;i++) {
            if (arg[0].matches( lq.get(i).queryEmail) && arg[1].matches(lq.get(i).queryPass)) {
                return true;
            }
        }

        return false;
    }

}
