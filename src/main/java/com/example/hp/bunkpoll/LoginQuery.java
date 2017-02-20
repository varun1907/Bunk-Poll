package com.example.hp.bunkpoll;

/**
 * Created by HP on 2/18/2017.
 */
public class LoginQuery {

    public LoginQuery(String email, String pass) {

        queryEmail=email;
        queryPass=pass;
    }



    private static String  queryEmail;
  private static String queryPass;



    public  String getQueryEmail() {
        return queryEmail;
    }

    public  String getQueryPass() {
        return queryPass;
    }
}
