package com.example.gupta.sugarcane;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CustomAdapter extends ArrayAdapter<String> {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date = new Date();

    Deal deal;
    private int layout;



    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String details = getItem(position);
        ViewHolder viewHolder;
        ViewHolder mainViewHolder = null;
        final String id = details.substring(details.lastIndexOf("ID of deal: ") + 12);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dealInfo = convertView.findViewById(R.id.dealinfo);
            viewHolder.accept = convertView.findViewById(R.id.accept);
            viewHolder.reject = convertView.findViewById(R.id.reject);

            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Deals").child(id);
                    final DatabaseReference databaseReferenceHistory = firebaseDatabase.getReference().child("History");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            deal = new Deal();
                            deal = dataSnapshot.getValue(Deal.class);

                            UserProfile userProfile = new UserProfile(
                                    deal.getUid(),
                                    deal.getName(),
                                    deal.getContact(),
                                    deal.getAddress(),
                                    deal.getLandarea(),
                                    deal.getSow(),
                                    deal.getReap(),
                                    id,
                                    formatter.format(date),
                                    "ACCEPTED"
                            );
                            databaseReferenceHistory.child(id).setValue(userProfile);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    databaseReference.setValue(null);
                    TastyToast.makeText(getContext(), "deal no "+id+" accepted", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                }
            });



            /*Now set listener for reject button*/
            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Deals").child(id);
                    final DatabaseReference databaseReferenceHistory = firebaseDatabase.getReference().child("History");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            deal = new Deal();
                            deal = dataSnapshot.getValue(Deal.class);

                            UserProfile userProfile = new UserProfile(
                                    deal.getUid(),
                                    deal.getName(),
                                    deal.getContact(),
                                    deal.getAddress(),
                                    deal.getLandarea(),
                                    deal.getSow(),
                                    deal.getReap(),
                                    id,
                                    formatter.format(date),
                                    "REJECTED"
                            );
                            databaseReferenceHistory.child(id).setValue(userProfile);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    databaseReference.setValue(null);
                    TastyToast.makeText(getContext(), "deal no "+id+" rejected", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                }
            });
            convertView.setTag(viewHolder);
        }
        {
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.dealInfo.setText(details);

        }

        return convertView;
    }


}

class ViewHolder{
    TextView dealInfo;
    Button accept;
    Button reject;
}
