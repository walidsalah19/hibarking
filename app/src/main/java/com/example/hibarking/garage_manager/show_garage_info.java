package com.example.hibarking.garage_manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hibarking.R;
import com.example.hibarking.SharedPref;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class show_garage_info extends Fragment {
private ProgressBar progressBar;
     private EditText name,price,num_unit,city,rate;
     private ImageButton location,paper;
     private FirebaseFirestore database;
     private String garage_id,latitude,longitude,url;
     SharedPref sharedPref;
        String urls;
        PDFView pdfView;
        ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(getActivity());
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_garage_info, container, false);
        garage_id=getArguments().getString("garage_id").toString();
        progressBar_method(v);
        intialization_tool(v);
        get_garage_data();
        download_file(v);
        return v;
    }

    private void download_file(View v) {
        paper=v.findViewById(R.id.sh_garage_paper);
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getActivity().startActivity(intent);
            }
        });
    }

    private void intialization_tool(View v)
    {
        name=v.findViewById(R.id.sh_garage_name);
        city=v.findViewById(R.id.sh_garage_city);
        price=v.findViewById(R.id.sh_garage_hour_price);
        num_unit=v.findViewById(R.id.sh_garage_name);
        location=v.findViewById(R.id.sh_garage_location);
        rate=v.findViewById(R.id.sh_garage_hour_rate);
        paper=v.findViewById(R.id.sh_garage_paper);
    }


    private void progressBar_method(View v) {
        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setProgress(90);
    }
    private void get_garage_data()
    {
        database=FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        database.collection("garage_requist").document(garage_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    name.setText(task.getResult().get("garage_name").toString());
                    city.setText(task.getResult().get("garage_name").toString());
                    price.setText(task.getResult().get("garage_name").toString());
                    num_unit.setText(task.getResult().get("garage_name").toString());
                    latitude=task.getResult().get("latitude").toString();
                    longitude=task.getResult().get("longitude").toString();
                    url=task.getResult().get("garage_paper").toString();
                    dialog.dismiss();
                }
            }
        });
    }
    private void getrate()
    {
        double rate_num=0;
        database.collection("garage_rate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.get("garage_id").toString();
                        if (garage_id.equals(id))
                        {

                        }
                    }
                }
            }
        });
    }

}