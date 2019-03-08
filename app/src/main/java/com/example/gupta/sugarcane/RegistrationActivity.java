package com.example.gupta.sugarcane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username, email, password;
    private Button register;
    private TextView login;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        bar.setTitle(Html.fromHtml("<font color='#000000'>Create new account</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/
        bar.hide();


        progressDialog = new ProgressDialog(this);

        setupUIViews();

        auth = FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    String useremail = email.getText().toString();
                    String userpass  = password.getText().toString();


                    auth.createUserWithEmailAndPassword(useremail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if(task.isSuccessful()){
                                FancyToast.makeText(RegistrationActivity.this, "New Account Created, Welcome!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            }else{
                                FancyToast.makeText(RegistrationActivity.this, "Registration Failed, Try again", FancyToast.LENGTH_LONG, FancyToast.CONFUSING, true).show();
                            }
                        }
                    });
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews(){
        //username = (EditText)findViewById(R.id.name);
        email    = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);
        login    = (TextView)findViewById(R.id.loginText);
    }

    private boolean validate(){
        //String name = username.getText().toString();
        String pass = password.getText().toString();
        String mail = email.getText().toString();

        if(/*name.isEmpty() || */pass.isEmpty() || mail.isEmpty()){
            FancyToast.makeText(RegistrationActivity.this, "Please enter all details", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
            return false;
        }
        return true;
    }
}
