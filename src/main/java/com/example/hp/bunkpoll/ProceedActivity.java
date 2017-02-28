package com.example.hp.bunkpoll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.bunkpoll.MongoDatabase.SaveAsyncTask;

import java.net.UnknownHostException;


public class ProceedActivity extends AppCompatActivity {


    EditText registrationCollege;
    EditText registrationBranch;
    EditText registrationSem;
    EditText registrationSection;
    EditText registrationPhone;
    Button register;
    RegisterInfo registerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);

        registrationCollege = (EditText) findViewById(R.id.regis_text_college);
        registrationBranch = (EditText) findViewById(R.id.regis_text_branch);
        registrationSem = (EditText) findViewById(R.id.regis_text_sem);
        registrationSection = (EditText) findViewById(R.id.regis_text_section);
        registrationPhone = (EditText) findViewById(R.id.regis_text_phone);
        register = (Button) findViewById(R.id.button_register);


        registerInfo = new RegisterInfo();


        registerInfo.name = getIntent().getExtras().getString("regis_name");
        registerInfo.email = getIntent().getExtras().getString("regis_email");
        registerInfo.pass = getIntent().getExtras().getString("regis_pass");


        registerInfo.college = registrationCollege.getText().toString();
        registerInfo.branch = registrationBranch.getText().toString();
        registerInfo.sem = registrationSem.getText().toString();
        registerInfo.section = registrationSection.getText().toString();
        registerInfo.phone = registrationPhone.getText().toString();


    }


    public void saveInfo(View view) throws UnknownHostException {


        signUp();

    }

    public void signUp() {

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        register.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(ProceedActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        registerInfo = new RegisterInfo();


        registerInfo.name = getIntent().getExtras().getString("regis_name");
        registerInfo.email = getIntent().getExtras().getString("regis_email");
        registerInfo.pass = getIntent().getExtras().getString("regis_pass");


        registerInfo.college = registrationCollege.getText().toString();
        registerInfo.branch = registrationBranch.getText().toString();
        registerInfo.sem = registrationSem.getText().toString();
        registerInfo.section = registrationSection.getText().toString();
        registerInfo.phone = registrationPhone.getText().toString();

        SaveAsyncTask tsk = new SaveAsyncTask();
        tsk.execute(registerInfo);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignUpSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
        Intent intermediateActivity = new Intent(ProceedActivity.this, IntermediateActivity.class);
        startActivity(intermediateActivity);

    }

    public void onSignUpSuccess()

    {
        register.setEnabled(true);
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Unable to proceed", Toast.LENGTH_LONG).show();

        register.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        registerInfo = new RegisterInfo();

        registerInfo.college = registrationCollege.getText().toString();
        registerInfo.branch = registrationBranch.getText().toString();
        registerInfo.sem = registrationSem.getText().toString();
        registerInfo.section = registrationSection.getText().toString();
        registerInfo.phone = registrationPhone.getText().toString();


        if (registerInfo.college.isEmpty() || registerInfo.college.length() < 3) {
            registrationCollege.setError("at least 3 characters");
            valid = false;
        } else {
            registrationCollege.setError(null);
        }

        if (registerInfo.branch.isEmpty()) {
            registrationBranch.setError("enter a valid email address");
            valid = false;
        } else {
            registrationBranch.setError(null);
        }

        if (registerInfo.sem.isEmpty() || registerInfo.sem.length() > 10) {
            registrationSem.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            registrationSem.setError(null);
        }

        if (registerInfo.section.isEmpty() || registerInfo.section.length() > 10) {
            registrationSection.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            registrationSection.setError(null);
        }

        if (registerInfo.phone.isEmpty() || registerInfo.phone.length() > 10 || registerInfo.phone.length() < 10) {
            registrationPhone.setError("valid phone number");
            valid = false;
        } else {
            registrationPhone.setError(null);
        }


        return valid;
    }


}
