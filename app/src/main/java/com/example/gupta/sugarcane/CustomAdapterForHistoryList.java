package com.example.gupta.sugarcane;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class CustomAdapterForHistoryList extends ArrayAdapter<String> {

    private int layout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public CustomAdapterForHistoryList(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        boolean accepted=false;

        final String details = getItem(position);
        ViewHolder2 viewHolder;
        ViewHolder2 mainViewHolder = null;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder2();
            viewHolder.historyInfo = convertView.findViewById(R.id.historyinfo);

            /*if(details.contains("ACCEPTED")){
                accepted = true;
            }
            else if(details.contains("REJECTED")){
                accepted = false;
            }*/
            convertView.setTag(viewHolder);
        }
        {
            mainViewHolder = (ViewHolder2) convertView.getTag();
            mainViewHolder.historyInfo.setText(details);
            if(details.contains("ACCEPTED")){
                mainViewHolder.historyInfo.setTextColor(Color.parseColor("#48ab75"));
            }else{
                mainViewHolder.historyInfo.setTextColor(Color.parseColor("#c45e70"));
            }

        }

        return convertView;
    }


}



class ViewHolder2{
    TextView historyInfo;
}