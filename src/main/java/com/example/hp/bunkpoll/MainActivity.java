package com.example.hp.bunkpoll;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        super.onCreate(savedInstanceState);
        if(networkInfo!=null && networkInfo.isConnected())
        {
            setContentView(R.layout.activity_main);
            Button buttonRegister=(Button)findViewById(R.id.button_register);
            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,RegisterActivity.class);

                    startActivity(intent);
                }
            });
        }

        else
        {
            setContentView(R.layout.empty);
            Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show();
        }


    }
}
