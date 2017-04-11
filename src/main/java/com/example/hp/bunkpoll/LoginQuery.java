package com.example.hp.bunkpoll;

/**
 * Created by HP on 2/18/2017.
 */
public class LoginQuery {


    public  String  queryEmail;
    public  String queryPass;

    public LoginQuery(String email, String pass) {

        queryEmail=email;
        queryPass=pass;
    }


    public  String getQueryEmail() {
        return queryEmail;
    }

    public  String getQueryPass() {
        return queryPass;
    }
}
