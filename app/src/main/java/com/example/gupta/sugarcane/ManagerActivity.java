package com.example.gupta.sugarcane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class ManagerActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ListView listView;
    ArrayList<String> list;
    CustomAdapter customAdapter;
    private ProgressDialog progressDialog;
    Deal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        android.support.v7.app.ActionBar bar = getSupportActionBar();/*action bar/title bar*/
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>Manager's Desk : Notifications</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Requests...");
        progressDialog.show();



        deal = new Deal();
        listView = (ListView)findViewById(R.id.listview);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list = new ArrayList<>();

        customAdapter = new CustomAdapter(this, R.layout.deal_info, list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();


                for(DataSnapshot snapshot_of_deals: dataSnapshot.child("Deals").getChildren()){
                    deal = null;
                    deal = snapshot_of_deals.getValue(Deal.class);

                    list.add("NAME      : " + deal.getName()    +"\n"+
                             "CONTACT   : " + deal.getContact() +"\n"+
                             "ADDRESS   : " + deal.getAddress() +"\n"+
                             "FARM AREA : " + deal.getLandarea()+"\n"+
                             "SOW[Date] : " + deal.getSow()     +"\n"+
                             "REAP[Date]: " + deal.getReap()    +"\n"+
                             "ID of deal: " + deal.getDateid() );
                }
                progressDialog.dismiss();
                listView.setAdapter(customAdapter);
                listView.setBackgroundResource(R.drawable.blueshade1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ManagerActivity.this, MainActivity.class));
    }
}


