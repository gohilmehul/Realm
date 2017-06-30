package com.mg.realmdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SavedataActivity extends AppCompatActivity {

    Realm realm;
    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedata);

        realm = Realm.getDefaultInstance();

        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RealmQuery query = realm.where(getSetData.class);
        RealmResults results = query.findAll();

        adapter = new Adapter(results, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialogbox();
            }
        });
    }

    private void addDialogbox(){

        Button btnInsert;
        final   EditText name,mobile,city;

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_save_data);

        btnInsert=(Button)dialog.findViewById(R.id.btn_add);
        name=(EditText)dialog.findViewById(R.id.name);
        mobile=(EditText)dialog.findViewById(R.id.mobileno);
        city=(EditText)dialog.findViewById(R.id.city);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<getSetData> cities = new ArrayList<>();
                getSetData getSetData= new getSetData();
                Log.e("System",""+System.currentTimeMillis());
                getSetData.setId(System.currentTimeMillis());
                getSetData.setName(name.getText().toString().trim());
                getSetData.setMobile(mobile.getText().toString().trim());
                getSetData.setCity(city.getText().toString().trim());

                cities.add(getSetData);

                realm.beginTransaction();
                realm.copyToRealm(cities);
                realm.commitTransaction();
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
