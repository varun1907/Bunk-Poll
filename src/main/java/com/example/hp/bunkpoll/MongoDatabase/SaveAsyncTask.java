package com.example.hp.bunkpoll.MongoDatabase;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.example.hp.bunkpoll.RegisterInfo;

/**
 * Created by HP on 2/15/2017.
 */

public class SaveAsyncTask extends AsyncTask<RegisterInfo,Void,Boolean> {


    @Override
    protected Boolean doInBackground(RegisterInfo... arg0) {

        try{

            RegisterInfo registerInfo=arg0[0];
            QueryBuilder queryBuilder=new QueryBuilder();
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost request=new HttpPost(queryBuilder.buildInfosSaveURL());
            StringEntity params=new StringEntity(queryBuilder.createInfo(registerInfo));
            request.addHeader("content-type","application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()<205)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            //e.getCause();
            String val = e.getMessage();
            String val2 = val;
            return false;
        }



    }
}
