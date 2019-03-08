package com.example.gupta.sugarcane;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button register;
    private Button history;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
        bar.setTitle(Html.fromHtml("<font color='#14e2b2'>Welcome!</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/

        register = (Button)findViewById(R.id.register);
        history  = (Button)findViewById(R.id.history);

        register.setOnClickListener(new View.OnClickListener() { /*bla bla bla bla*/
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, ApplicationActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, HistoryActivity.class));
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.created : TastyToast.makeText(UserActivity.this, "App created on: \n23rd September, 2018.",
                    TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            break;
            case R.id.infoMenu  :
                        TastyToast.makeText(UserActivity.this, "   App made by   \n   Anjali G. Gupta   \n   <3   ",
                        TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            break;
            case R.id.logoutMenu: logout();
            break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void logout(){
        firebaseAuth.signOut();
        FancyToast.makeText(UserActivity.this, "Logged out",
                FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
        finish();
        startActivity(new Intent(UserActivity.this, MainActivity.class));
    }
}
