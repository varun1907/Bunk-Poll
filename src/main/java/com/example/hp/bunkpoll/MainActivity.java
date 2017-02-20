package com.example.hp.bunkpoll;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.bunkpoll.MongoDatabase.GetInfoAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPass;
    Button loginButton;
    TextView registerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            setContentView(R.layout.activity_main);


            loginEmail = (EditText) findViewById(R.id.main_text_email);
            loginPass = (EditText) findViewById(R.id.main_text_pass);
            loginButton = (Button) findViewById(R.id.button_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });


            registerTextView = (TextView) findViewById(R.id.register_text_view);
            registerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                    startActivity(intent);
                }
            });
        }
        else {
            setContentView(R.layout.empty);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = loginEmail.getText().toString();
        String pass = loginPass.getText().toString();


        GetInfoAsyncTask task = new GetInfoAsyncTask();
        try {
            boolean authenticate=task.execute(email,pass).get();
            if(authenticate)
            {
                Intent testIntent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(testIntent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Chech your details again..!!",Toast.LENGTH_LONG);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }




    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed Try again", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

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