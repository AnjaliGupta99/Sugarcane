
package com.example.gupta.sugarcane;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.sdsmdg.tastytoast.TastyToast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    Switch manager;
    EditText username,password;
    Button login;
    TextView attempts;
    TextView register;
    TextView managerText;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bar.hide();



        register = (TextView)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        attempts = (TextView)findViewById(R.id.attempt);
        login    = (Button)findViewById(R.id.login);
        manager  = (Switch)findViewById(R.id.manager);
        managerText = (TextView)findViewById(R.id.managerText) ;



        attempts.setText("No of attempts Remaining:5");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        }



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    FancyToast.makeText(MainActivity.this, "please enter credentials", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(manager.isChecked())
                {
                        if(username.getText().toString().equalsIgnoreCase("anjaliggupta99@gmail.com")
                                && password.getText().toString().equals("AnjaliGupta")){
                            startActivity(new Intent(MainActivity.this, ManagerActivity.class));
                        }
                }
                else
                validate(username.getText().toString(), password.getText().toString());
            }
        });

        manager.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(manager.isChecked())
                    managerText.setTextColor(Color.parseColor("#ffffff"));
                else
                    managerText.setTextColor(Color.parseColor("#FF181E1E"));
            }
        });
    }


    private void validate(String username, String password){

        progressDialog.setMessage("Checking Credentials");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    FancyToast.makeText(MainActivity.this, "Welcome!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                }else{
                    TastyToast.makeText(MainActivity.this, "Login Failed",
                            TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                    counter--;
                    progressDialog.dismiss();
                    if(counter>=0){
                        attempts.setText("No of attempts Remaining:"+counter);
                    }else{
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}
