package com.example.gupta.sugarcane;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationActivity extends AppCompatActivity {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date = new Date();

    EditText startDate, endDate, name, address, contact, landArea;
    Button register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        android.support.v7.app.ActionBar bar = getSupportActionBar();/*action bar/title bar*/
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>Application for deal</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/

        firebaseAuth = FirebaseAuth.getInstance();

        startDate = (EditText)findViewById(R.id.sow);
        endDate   = (EditText)findViewById(R.id.reap);
        name      = (EditText)findViewById(R.id.name);
        contact   = (EditText)findViewById(R.id.contact);
        address   = (EditText)findViewById(R.id.address);
        register  = (Button)findViewById(R.id.register);

        landArea = (EditText)findViewById(R.id.area);
        setHints();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(invalid()){
                    FancyToast.makeText(ApplicationActivity.this, "Please enter all details",
                            FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }else{
                    sendUserData();
                    FancyToast.makeText(ApplicationActivity.this, "Deal sent! GoodLuck :)",
                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    finish();
                    startActivity(new Intent(ApplicationActivity.this, UserActivity.class));
                }
            }
        });
    }

    private boolean invalid(){
        if(     name.getText().toString().isEmpty()      ||
                contact.getText().toString().isEmpty()   ||
                address.getText().toString().isEmpty()   ||
                startDate.getText().toString().isEmpty() ||
                endDate.getText().toString().isEmpty()   ||
                landArea.getText().toString().isEmpty()    ){
            return true;
        }else{
            return false;
        }
    }


    private void setHints(){
        if(startDate.getText().toString().isEmpty())
        {
            startDate.setHint("sowing [Date]");
            startDate.setHintTextColor(Color.rgb(50, 79, 91));
        }
        if(endDate.getText().toString().isEmpty())
        {
            endDate.setHint("probable reap [Date]");
            endDate.setHintTextColor(Color.rgb(50, 79, 91));
        }
        if(name.getText().toString().isEmpty())
        {
            name.setHint("full name");
            name.setHintTextColor(Color.rgb(50, 79, 91));
        }
        if(contact.getText().toString().isEmpty())
        {
            contact.setHint("contact");
            contact.setHintTextColor(Color.rgb(50, 79, 91));
        }
        if(address.getText().toString().isEmpty())
        {
            address.setHint("address of farm");
            address.setHintTextColor(Color.rgb(50, 79, 91));
        }
        if(landArea.getText().toString().isEmpty())
        {
            landArea.setHint("land area [provide units manually]");
            landArea.setHintTextColor(Color.rgb(50, 79, 91));
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Deals");
        String date_ = formatter.format(date);

        UserProfile userProfile = new UserProfile(
                firebaseAuth.getUid(),
                name.getText().toString(),
                contact.getText().toString(),
                address.getText().toString(),
                startDate.getText().toString(),
                endDate.getText().toString(),
                date_,
                landArea.getText().toString()

        );
        databaseReference.child(date_).setValue(userProfile);

    }
}
