package com.example.gupta.sugarcane;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    CustomAdapterForHistoryList customAdapterForHistoryList;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*TITLE BAR & NOTIFICATION BAR SETTINGS*/
        android.support.v7.app.ActionBar bar = getSupportActionBar();/*action bar/title bar*/
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>User History</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading History...");
        progressDialog.show();




        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        listView = (ListView)findViewById(R.id.listviewHistory);                                    //list view
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //databaseReference = firebaseDatabase.getReference.child("History");
        //databaseReference = firebaseDatabase.getReference.child("History").getchildren;
        list = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this, R.layout.history_item_info, R.id.historyinfo/*TextView*/, list);
        customAdapterForHistoryList = new CustomAdapterForHistoryList(this, R.layout.history_item_info, list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                History history = new History();
                list.clear();
                for(DataSnapshot snapshot_of_history: dataSnapshot.child("History").getChildren()){     //got all deals

                    history = null;
                    history = snapshot_of_history.getValue(History.class);

                    if((history.getUid()).equals(user.getUid())) {
                        list.add("NAME         : " + history.getName()   + "\n" +
                                "CONTACT      : " + history.getContact() + "\n" +
                                "ADDRESS      : " + history.getAddress() + "\n" +
                                "FARM AREA    : " + history.getLandarea()+ "\n" +
                                "SOW[Date]    : " + history.getSow()     + "\n" +
                                "REAP[Date]   : " + history.getReap()    + "\n" +
                                "ID of deal   : " + history.getDateid()  + "\n" +
                                "Deal done on : " + history.getTimeaccepted()  + "\n" +
                                "STATUS       : [ "+ history.getStatus()+ " ]");
                    }
                }
                //listView.setAdapter(adapter);
                progressDialog.dismiss();
                listView.setAdapter(customAdapterForHistoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
