package com.example.hp.bunkpoll.MongoDatabase;

import com.example.hp.bunkpoll.RegisterInfo;

/**
 * Created by HP on 2/15/2017.
 */

public class QueryBuilder {


    /**
     * Specify your database name here
     * @return
     */
    public String getDatabaseName() {
        return "code101";
    }

    /**
     * Specify your MongoLab API here
     * @return
     */
    public String getApiKey() {
        return "gzMe01lMw8Veh4YJStGp0ZwVdZZLm312";
    }

    /**
     * This constructs the URL that allows you to manage your database,
     * collections and documents
     * @return
     */
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    /**
     * Completes the formating of your URL and adds your API key at the end
     * @return
     */
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    /**
     * Returns the docs101 collection
     * @return
     */
    public String documentRequest()
    {
        return "docs101";
    }


    public String buildInfoGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }



    /**
     * Builds a complete URL using the methods specified above
     * @return
     */
    public String buildInfosSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * Formats the contact details for MongoHQ Posting
     * @param ri: Details of the person
     * @return
     */





    public String createInfo(RegisterInfo ri)
    {




        return String
                .format("{\"name\":\"%s\" ,  \"email\": \"%s\", "
                                + "\"pass\"   : \"%s\"  ,  \"college\": \"%s\","
                                + "\"branch\" : \"%s\", "
                                + "\"sem\"    : \"%s\" , \"section\": \"%s\","
                                + "\"phone\"  : \"%s\" } , \"safe\" : true}",
                                ri.name,ri.email,ri.pass,ri.college,ri.branch,ri.sem,ri.section,ri.phone);
    }



}


