package com.example.hp.bunkpoll;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.bunkpoll.MongoDatabase.GetInfoAsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPass;
    CheckBox remember;
    Button loginButton;
    TextView registerTextView;
  //  ProgressDialog progressDialog;

    public static String PREFS_NAME = "mypre";
    public static String PREF_EMAIL = "email";
    public static String PREF_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check Internet Connectivity

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            setContentView(R.layout.activity_main);


            registerTextView = (TextView) findViewById(R.id.register_text_view);
            registerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                    startActivity(intent);
                }
            });
        } else {
            setContentView(R.layout.empty);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //read email and password from SharedPreferences
        getUser();
    }


    // onClick method for login button

    public void login(View view) {

     //  pd = new ProgressDialog(MainActivity.this);
     //   pd.setMessage("Loading....");
     // pd.setTitle("Authenticating");
     //  pd.setIndeterminate(true);
     //   pd.show();


        loginEmail = (EditText) findViewById(R.id.main_text_email);
        loginPass = (EditText) findViewById(R.id.main_text_pass);
        loginButton = (Button) findViewById(R.id.button_login);

        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(true);

    //          final  ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
    //                   R.style.AppTheme);
   //            progressDialog.setIndeterminate(true);
   //            progressDialog.setMessage("Authenticating...");
   //            progressDialog.show();




        String email = loginEmail.getText().toString();
        String pass = loginPass.getText().toString();
        remember = (CheckBox) findViewById(R.id.remember_check);
        if (remember.isChecked()) {
            rememberMe(email, pass);
        }

        // retreive data from database to check login info
        GetInfoAsyncTask task = new GetInfoAsyncTask();
        try {
            boolean authenticate = task.execute(email, pass).get();
            if (authenticate) {
                Intent testIntent = new Intent(MainActivity.this, IntermediateActivity.class);
                startActivity(testIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Chech your details again..!!", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                      //  pd.dismiss();
     //                   progressDialog.dismiss();
                    }
                }, 3000);


    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    // reading locally saved email and password

    public void getUser() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_EMAIL, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            //directly show intermediate activity
            showIntermediate();
        }

    }

    //storing email and password locally

    public void rememberMe(String user, String password) {
        //save username and password in SharedPreferences
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_EMAIL, user)
                .putString(PREF_PASSWORD, password)
                .apply();
    }


    //intent to open intermediate activity

    public void showIntermediate() {
        //display log out activity
        Intent intent = new Intent(this, IntermediateActivity.class);
        //  intent.putExtra("user",username);
        startActivity(intent);
    }


    //method when login action is fulfilled

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();

    }


    // method when login action is failed

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed Try again", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    //    pd.dismiss();

    }

  //  @Override
 //   protected void onStop() {
 //       super.onStop();
 //           progressDialog.dismiss();
 //   }
    // method to check validation of email and password fields

    public boolean validate() {
        boolean valid = true;
        String email = loginEmail.getText().toString();
        String pass = loginPass.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("enter a valid email address");
            valid = false;
        } else {
            loginEmail.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            loginPass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            loginPass.setError(null);
        }

        return valid;

    }


}