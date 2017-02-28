package com.example.hp.bunkpoll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.UnknownHostException;

public class RegisterActivity extends AppCompatActivity {

    EditText registrationName;
    EditText registrationEmail;
    EditText registrationPassword;

    Button proceedButton;
    RegisterInfo registerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registrationName = (EditText) findViewById(R.id.regis_name);
        registrationEmail = (EditText) findViewById(R.id.regis_email);
        registrationPassword = (EditText) findViewById(R.id.regis_password);
        proceedButton=(Button)findViewById(R.id.proceed);


    }

    public void saveInfo(View view) throws UnknownHostException {

       // Intent intent = new Intent(RegisterActivity.this, ProceedActivity.class);
      //  startActivity(intent);

      //  proceedButton.setEnabled(true);
      //  SaveAsyncTask tsk = new SaveAsyncTask();
     //   tsk.execute(registerInfo);
     //   Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();

                proceed();

    }

    public void proceed(){

        if(!validate())
        {
            onProceedFailed();
            return;
        }
        proceedButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        registerInfo = new RegisterInfo();
        registerInfo.name = registrationName.getText().toString();
        registerInfo.email = registrationEmail.getText().toString();
        registerInfo.pass = registrationPassword.getText().toString();


     //   SaveAsyncTask tsk = new SaveAsyncTask();
     //   tsk.execute(registerInfo);
        Intent intent = new Intent(RegisterActivity.this, ProceedActivity.class);
        intent.putExtra("regis_name",registerInfo.name);
        intent.putExtra("regis_email",registerInfo.email);
        intent.putExtra("regis_pass",registerInfo.pass);
         startActivity(intent);

    /**    new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onProceedSuccess();
                        // onSignupFailed();
                       progressDialog.dismiss();
                    }
               }, 3000);
**/
       onProceedSuccess();
        // onSignupFailed();
      //  progressDialog.dismiss();
    }

    public void onProceedSuccess()

    {
        proceedButton.setEnabled(true);
    }
    public void onProceedFailed() {
        Toast.makeText(getApplicationContext(), "Unable to proceed", Toast.LENGTH_LONG).show();

        proceedButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        registerInfo = new RegisterInfo();
        registerInfo.name = registrationName.getText().toString();
        registerInfo.email = registrationEmail.getText().toString();
        registerInfo.pass = registrationPassword.getText().toString();

        if (registerInfo.name.isEmpty() || registerInfo.name.length() < 3) {
            registrationName.setError("at least 3 characters");
            valid = false;
        } else {
            registrationName.setError(null);
        }

        if (registerInfo.email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(registerInfo.email).matches()) {
            registrationEmail.setError("enter a valid email address");
            valid = false;
        } else {
            registrationEmail.setError(null);
        }

        if (registerInfo.pass.isEmpty() || registerInfo.pass.length() < 4 || registerInfo.pass.length() > 10) {
            registrationPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            registrationPassword.setError(null);
        }

        return valid;
    }

}